package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.Picks;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.model.Winner;
import org.lastbubble.shliktr.service.PoolService;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @version $Id$
 */
public class EditPicksController extends SimpleFormController
{
	private PoolService poolService;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public EditPicksController()
	{
		setCommandName("picks");
		setCommandClass(Picks.class);
		setFormView("editPicks");
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
	protected Map referenceData(
		HttpServletRequest request, Object command, Errors errors )
	throws Exception
	{
		Map map = new HashMap();
		map.put("player", WebUtils.getPlayerFromRequest(request));
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

		Player player = WebUtils.getPlayerFromRequest(request);

		if( player == null || player.getUsername().equals("eric") )
		{
			int playerId = 0;

			s = request.getParameter("playerId");
			if( s != null && s.length() > 0 )
			{
				try { playerId = Integer.parseInt(s); }
				catch( NumberFormatException e ) { }
			}

			if( playerId > 0 )
			{
				player = this.poolService.findPlayerById(playerId);
			}

			if( player == null )
			{
				throw new IllegalArgumentException("Invalid player id: "+s);
			}
		}

		if( player == null )
		{
			throw new IllegalArgumentException("No player specified");
		}

		return this.poolService.findPicksForPlayer(week, player, true);
	}

	@Override
	protected ModelAndView onSubmit( Object command ) throws Exception
	{
		Picks picks = (Picks) command;

		Integer weekId = picks.getWeek().getId();

		Map model = new HashMap();
		model.put("weekId", picks.getWeek().getId());
		model.put("playerId", picks.getPlayer().getId());

		if( this.poolService.acceptPicksForWeek(weekId) == false )
		{
			return new ModelAndView("viewPicks", model);
		}

		this.poolService.makePersistentPicks(picks);

		return new ModelAndView(getSuccessView(), model);
	}

}	// End of EditPicksController
