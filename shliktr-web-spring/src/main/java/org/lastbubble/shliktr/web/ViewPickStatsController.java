package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.PickStats;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.service.PoolService;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @version $Id$
 */
public class ViewPickStatsController extends AbstractController
{
	private PoolService poolService;


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public void setPoolService( PoolService ps ) { this.poolService = ps; }


	//-------------------------------------------------------------------------
	// AbstractController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleRequestInternal(
		HttpServletRequest request, HttpServletResponse response )
	throws Exception
	{
		Map model = new HashMap();

		int weekId = 1;

		String s = request.getParameter("weekId");
		if( s != null && s.length() > 0 )
		{
			try { weekId = Integer.parseInt(s); }
			catch( NumberFormatException e ) { }
		}

		Week week = this.poolService.findWeekById(weekId);
		model.put("week", week);

		Player player = WebUtils.getPlayerFromRequest(request);
		model.put("player", player);

		List<PickStats> statsList = this.poolService.findPickStatsForWeek(week);
		model.put("statsList", statsList);

		return new ModelAndView("viewPickStats", model);
	}

}
