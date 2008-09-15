package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PickStats;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

/**
 * @version $Id$
 */
public class ViewPickStatsController extends WeekController
{
	//-------------------------------------------------------------------------
	// WeekController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleWeek( ModelAndView modelAndView,
		IWeek week, IPlayer player )
	{
		List<PickStats> statsList = this.poolService.findPickStatsForWeek(week);
		return modelAndView.addObject("statsList", statsList);
	}
}
