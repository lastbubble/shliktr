package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.PickStats;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.Week;

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
		Week week, Player player )
	{
		List<PickStats> statsList = this.poolService.findPickStatsForWeek(week);
		return modelAndView.addObject("statsList", statsList);
	}

}
