package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.service.PoolService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @version $Id$
 */
abstract class WeekController extends AbstractController
{
	protected PoolService poolService;

	private String viewName;


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public void setViewName( String s ) { this.viewName = s; }

	public void setPoolService( PoolService ps ) { this.poolService = ps; }


	//-------------------------------------------------------------------------
	// AbstractController methods
	//-------------------------------------------------------------------------

	@Override
	protected final ModelAndView handleRequestInternal(
		HttpServletRequest request, HttpServletResponse response )
	throws Exception
	{
		Week week = null;
		String s = request.getParameter("weekId");
		if( s != null )
		{
			int weekId = 0;

			try { weekId = Integer.parseInt(s); }
			catch( NumberFormatException e ) { }

			if( weekId > 0 )
			{
				week = this.poolService.findWeekById(weekId);
			}
		}
		else
		{
			week = this.poolService.findCurrentWeek();
		}

		ModelAndView modelAndView = new ModelAndView(this.viewName);
		modelAndView.addObject("week", week);

		Player player = WebUtils.getPlayerFromRequest(request);
		modelAndView.addObject("player", player);

		return handleWeek(modelAndView, week, player, request);
	}


	//-------------------------------------------------------------------------
	// Template methods
	//-------------------------------------------------------------------------

	protected ModelAndView handleWeek( ModelAndView modelAndView,
		Week week, Player player, HttpServletRequest request )
	{
		return handleWeek(modelAndView, week, player);
	}

	protected ModelAndView handleWeek( ModelAndView modelAndView,
		Week week, Player player )
	{
		return modelAndView;
	}

}
