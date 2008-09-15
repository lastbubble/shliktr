package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PlayerScore;

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

		List<PlayerScore> scores = this.poolService.findScoresForWeek(week);
		modelAndView.addObject("scores", scores);

		List<PlayerScore> closest = new ArrayList<PlayerScore>();

		int min = Integer.MAX_VALUE;
		for( PlayerScore score : scores )
		{
			if( score.getTiebreakerDiff() < min )
			{
				closest.clear();
				closest.add(score);
				min = score.getTiebreakerDiff();
			}
			else if( score.getTiebreakerDiff() == min )
			{
				closest.add(score);
			}
		}
		modelAndView.addObject("closest", closest);

		return modelAndView;
	}
}