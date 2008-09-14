package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.model.Pick;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.model.Winner;
import org.lastbubble.shliktr.service.PoolService;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @version $Id$
 */
public class AddPicksController extends SimpleFormController
{
	private PoolService poolService;


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

		Week week = this.poolService.findWeekById(weekId);

		List<Player> allPlayers = this.poolService.findAllPlayers();
		Set<Player> weekPlayers = this.poolService.findPlayersForWeek(week);

		List<Player> players = new ArrayList<Player>(allPlayers.size());
		for( Player player : allPlayers )
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

		Week week = this.poolService.findWeekById(weekId);

		if( week == null )
		{
			throw new IllegalArgumentException("Invalid week id: "+s);
		}

		return new NewPicksForm(week);
	}

	@Override
	protected ModelAndView onSubmit( Object command ) throws Exception
	{
		NewPicksForm picksForm = (NewPicksForm) command;

		int weekId = picksForm.getWeekId();
		Week week = this.poolService.findWeekById(weekId);

		int playerId = picksForm.getPlayerId();
		Player player = this.poolService.findPlayerById(playerId);

		if( this.poolService.acceptPicksForWeek(weekId) == false )
		{
			ModelAndView mv = new ModelAndView("viewPicks");
			mv.addObject("weekId", week.getId());
			mv.addObject("playerId", player.getId());
			return mv;
		}

		Pick[] newPicks = picksForm.getPicks();

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

		this.poolService.saveEntry(entry);

		ModelAndView mv = new ModelAndView(getSuccessView());
		mv.addObject("weekId", week.getId());
		mv.addObject("playerId", player.getId());

		return mv;
	}

}	// End of AddPicksController
