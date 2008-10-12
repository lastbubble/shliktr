package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IWeek;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/**
 * @version $Id$
 */
public class ViewRankingsController extends WeekController
{
	//-------------------------------------------------------------------------
	// WeekController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleWeek( ModelAndView modelAndView,
		IWeek week, IPlayer player, HttpServletRequest request )
	{
		String team = request.getParameter("team");

		modelAndView.addObject("team", team);

		Map<IPlayer, Integer> rankings = this.poolService
			.findPlayerRankingsForTeam(week.getWeekNumber(), team);

		return modelAndView.addObject("rankings", rankings);
	}
}
