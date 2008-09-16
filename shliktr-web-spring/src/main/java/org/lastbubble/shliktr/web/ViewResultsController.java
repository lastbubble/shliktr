package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;

import java.util.ArrayList;
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

		List<? extends IPoolEntry> entries = this.poolService
			.findEntriesForWeek(week);
		modelAndView.addObject("scores", entries);

		List<IPoolEntry> closest = new ArrayList<IPoolEntry>();

		int min = Integer.MAX_VALUE;
		for( IPoolEntry entry : entries )
		{
			if( entry.getTiebreakerDiff() < min )
			{
				closest.clear();
				closest.add(entry);
				min = entry.getTiebreakerDiff();
			}
			else if( entry.getTiebreakerDiff() == min )
			{
				closest.add(entry);
			}
		}
		modelAndView.addObject("closest", closest);

		return modelAndView;
	}
}