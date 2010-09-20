package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolAccount;
import org.lastbubble.shliktr.IPoolBank;
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

		StringBuilder buf = new StringBuilder()
			.append("PLAYER       SCORE  (W-L)\n");
		for( PoolResult result : results )
		{
			buf.append(String.format(
				"%-12s  %3d   %2d-%-2d\n",
				result.getPlayer().getName(),
				result.getPoints(),
				result.getGamesWon(),
				result.getGamesLost()
			));
		}
		modelAndView.addObject("resultOutput", buf.toString());

		IPoolBank bank = this.poolService.findBankForWeek(week.getWeekNumber());

		modelAndView.addObject("bank", bank);

		buf.setLength(0);
		for( IPoolAccount account : bank.getAccounts() )
		{
			int points = account.getPoints();
			String amount = String.format(
				"%s$%d.%02d",
				(points > 0) ? "" : "-",
				Math.abs(points / 100),
				(points % 100)
			);
			buf.append(String.format(
				"%-12s %7s\n",
				account.getPlayer().getName(),
				amount
			));
		}
		modelAndView.addObject("bankOutput", buf.toString());

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