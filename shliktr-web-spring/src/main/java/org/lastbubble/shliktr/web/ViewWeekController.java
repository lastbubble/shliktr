package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IWeek;

import java.util.Set;

import org.springframework.web.servlet.ModelAndView;

/**
 * @version $Id$
 */
public class ViewWeekController extends WeekController
{
	private static final int[] WEEK_IDS = new int[] {
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17
	};


	//-------------------------------------------------------------------------
	// WeekController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleWeek( ModelAndView modelAndView,
		IWeek week, IPlayer player )
	{
		modelAndView.addObject("weekIds", WEEK_IDS);

		Set<? extends IPlayer> players = this.poolService.findPlayersForWeek(week);

		if( player != null )
		{
			if( player.getUsername().equals("eric") )
			{
				modelAndView.addObject("isAdmin", Boolean.TRUE);
				modelAndView.addObject("players", players);
			}
			else
			{
				modelAndView.addObject("hasPicks", players.contains(player));
			}
		}

		StringBuilder buf = new StringBuilder();
		for( IGame game : week.getGames() )
		{
			buf.append(String.format(
				"%-13s  %-13s  _____________  ___________\n",
				game.getAwayTeam().getLocation(),
				game.getHomeTeam().getLocation()
			));
		}
		modelAndView.addObject("weekOutput", buf.toString());

		return modelAndView;
	}
}
