package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolAccount;
import org.lastbubble.shliktr.IPoolBank;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PoolResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/**
 * @version $Id$
 */
public class ViewHeadToHeadController extends WeekController
{
	//-------------------------------------------------------------------------
	// WeekController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleWeek( ModelAndView modelAndView,
		IWeek week, IPlayer player, HttpServletRequest request )
	{
		List<? extends IPlayer> allPlayers = this.poolService.findAllPlayers();
		modelAndView.addObject("allPlayers", allPlayers);

		List<IPlayer> players = new ArrayList<IPlayer>();
		List<String> playerNames = new ArrayList<String>();

		String[] values = request.getParameterValues("playerId");
		if( values != null )
		{
			for( String value : values )
			{
				try {
					player = this.poolService.findPlayerById(Integer.valueOf(value));
					if( player != null )
					{
						players.add(player);
						playerNames.add(player.getName());
					}

				} catch( NumberFormatException e ) {
					// ignore
				}
			}
		}

		if( players.isEmpty() ) { return modelAndView; }

		modelAndView.addObject("playerNames", playerNames);

		int currentWeekId = this.poolService.findCurrentWeek().getWeekNumber();

		List<List<PoolResult>> headToHead = new ArrayList<List<PoolResult>>(currentWeekId);

		for( int weekId = 1; weekId <= currentWeekId; weekId++ )
		{
			List<PoolResult> weekHeadToHead = new ArrayList<PoolResult>();
			headToHead.add(weekHeadToHead);

			List<PoolResult> weekResults = this.poolService.findResultsForWeek(weekId);
			for( PoolResult result : weekResults )
			{
				if( players.contains(result.getPlayer()) ) { weekHeadToHead.add(result); }
			}

			Collections.sort(weekHeadToHead);
		}

		modelAndView.addObject("results", headToHead);

		return modelAndView;
	}
}