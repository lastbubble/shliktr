package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PoolResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

/**
 * @version $Id$
 */
public class ViewResultsController extends WeekController
{
	//-------------------------------------------------------------------------
	// WeekController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleWeek( ModelAndView modelAndView,
		IWeek week, IPlayer player )
	{
		if( player != null && player.getUsername().equals("eric") )
		{
			modelAndView.addObject("isAdmin", Boolean.TRUE);
			modelAndView.addObject("players", this.poolService
				.findPlayersForWeek(week));
		}

		List<PoolResult> results = this.poolService
			.findResultsForWeek(week.getWeekNumber());

		modelAndView.addObject("results", results);

		List<PoolResult> closest = new ArrayList<PoolResult>();

		int min = Integer.MAX_VALUE;
		for( PoolResult result : results )
		{
			if( result.getTiebreakerDiff() < min )
			{
				closest.clear();
				closest.add(result);
				min = result.getTiebreakerDiff();
			}
			else if( result.getTiebreakerDiff() == min )
			{
				closest.add(result);
			}
		}
		modelAndView.addObject("closest", closest);

		return modelAndView;
	}
}