package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.Winner;
import org.lastbubble.shliktr.mail.IPoolMailer;
import org.lastbubble.shliktr.service.PoolService;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @version $Id$
 */
public class AddPicksController extends SimpleFormController
{
	private PoolService poolService;

	private IPoolMailer poolMailer;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public AddPicksController()
	{
		setCommandName("newPicks");
		setCommandClass(NewPicksForm.class);
		setFormView("addPicks");
		setSuccessView("redirect:/app/viewPicks");
	}


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public void setPoolService( PoolService ps ) { this.poolService = ps; }

	public void setPoolMailer( IPoolMailer pm ) { this.poolMailer = pm; }


	//-------------------------------------------------------------------------
	// SimpleFormController methods
	//-------------------------------------------------------------------------

	@Override
	protected void initBinder(
		HttpServletRequest req, ServletRequestDataBinder binder )
	throws Exception
	{
		binder.registerCustomEditor(Winner.class, new WinnerEditor());
	}

	@Override
	protected Map referenceData( HttpServletRequest request ) throws Exception
	{
		int weekId = 1;

		String s = request.getParameter("weekId");
		if( s != null && s.length() > 0 )
		{
			try { weekId = Integer.parseInt(s); }
			catch( NumberFormatException e ) { }
		}

		IWeek week = this.poolService.findWeekById(weekId);

		List<? extends IPlayer> allPlayers = this.poolService.findAllPlayers();
		Set<? extends IPlayer> weekPlayers = this.poolService
			.findPlayersForWeek(week);

		List<IPlayer> players = new ArrayList<IPlayer>(allPlayers.size());
		for( IPlayer player : allPlayers )
		{
			if( weekPlayers.contains(player) == false )
			{
				players.add(player);
			}
		}

		Map map = new HashMap();
		map.put("week", week);
		map.put("players", players);

		return map;
	}

	@Override
	protected Object formBackingObject( HttpServletRequest request )
	throws Exception
	{
		String s;

		int weekId = 1;

		s = request.getParameter("weekId");
		if( s != null && s.length() > 0 )
		{
			try { weekId = Integer.parseInt(s); }
			catch( NumberFormatException e ) { }
		}

		IWeek week = this.poolService.findWeekById(weekId);

		if( week == null )
		{
			throw new IllegalArgumentException("Invalid week id: "+s);
		}

		return new NewPicksForm(week);
	}

	@Override
	protected ModelAndView onSubmit(
		HttpServletRequest request,
		HttpServletResponse response,
		Object command,
		BindException errors ) throws Exception
	{
		NewPicksForm picksForm = (NewPicksForm) command;

		int weekId = picksForm.getWeekId();
		IWeek week = this.poolService.findWeekById(weekId);

		int playerId = picksForm.getPlayerId();
		IPlayer player = this.poolService.findPlayerById(playerId);

		if( this.poolService.acceptPicksForWeek(weekId) == false )
		{
			IPlayer user = WebUtils.getPlayerFromRequest(request);
			if( user == null || user.getUsername().equals("eric") == false )
			{
				ModelAndView mv = new ModelAndView("viewPicks");
				mv.addObject("weekId", week.getWeekNumber());
				mv.addObject("playerId", player.getId());
				return mv;
			}
		}

		IPick[] newPicks = picksForm.getPicks();

		IPoolEntry entry = this.poolService.findEntry(week, player, true);

		List<? extends IPick> picks = entry.getPicks();
		for( IPick pick : picks )
		{
			for( int i = 0; i < newPicks.length; i++ )
			{
				if( pick.getGame().equals(newPicks[i].getGame()) )
				{
					pick.setWinner(newPicks[i].getWinner());
					pick.setRanking(newPicks[i].getRanking());
				}
			}
		}

		entry.setTiebreaker(picksForm.getTiebreaker());

		ModelAndView mv;

		String[] errmsg = new String[1];

		if( entry.validate(errmsg) )
		{
			this.poolService.saveEntry(entry);

			mv = new ModelAndView(getSuccessView());

			if( this.poolMailer != null && this.poolMailer.isEnabled() )
			{
				this.poolMailer.mailEntry(entry);
			}
		}
		else
		{
			mv = new ModelAndView(getFormView());

			mv.addObject("week", week);
			mv.addObject("players", Collections.singletonList(player));
			mv.addObject("newPicks", picksForm);
			mv.addObject("errorMsg", errmsg[0]);
		}

		mv.addObject("weekId", week.getWeekNumber());
		mv.addObject("playerId", player.getId());

		return mv;
	}
}