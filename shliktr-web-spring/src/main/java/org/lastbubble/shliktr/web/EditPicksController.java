package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.Winner;
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
		setCommandClass(IPoolEntry.class);
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

		IWeek week = this.poolService.findWeekById(weekId);

		if( week == null )
		{
			throw new IllegalArgumentException("Invalid week id: "+s);
		}

		IPlayer player = WebUtils.getPlayerFromRequest(request);

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

		return this.poolService.findEntry(week, player, true);
	}

	@Override
	protected ModelAndView onSubmit( Object command ) throws Exception
	{
		IPoolEntry entry = (IPoolEntry) command;

		Integer weekId = entry.getWeek().getWeekNumber();

		Map model = new HashMap();
		model.put("weekId", weekId);
		model.put("playerId", entry.getPlayer().getId());

		if( this.poolService.acceptPicksForWeek(weekId) == false )
		{
			return new ModelAndView("viewPicks", model);
		}

		this.poolService.saveEntry(entry);

		return new ModelAndView(getSuccessView(), model);
	}
}