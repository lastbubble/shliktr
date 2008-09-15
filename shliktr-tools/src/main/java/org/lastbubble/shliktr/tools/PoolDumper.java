package org.lastbubble.shliktr.tools;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.ITeam;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.service.PoolService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @version $Id$
 */
public class PoolDumper
{
	private PoolService poolService;

	//---------------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------------

	public void setPoolService( PoolService poolService )
	{
		this.poolService = poolService;
	}


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	public void dump( int weekNumber, PrintWriter writer )
	throws IOException
	{
		writer.println("# dumping week "+weekNumber);

		IWeek week = this.poolService.findWeekById(weekNumber);

		if( week != null )
		{
			StringBuilder buf = new StringBuilder()
				.append("week").append(weekNumber).append(';')
				.append(week.getTiebreakerAnswer()).append(';');

			Map<ITeam, Integer> scores = new HashMap<ITeam, Integer>();

			List<? extends IGame> games = week.getGames();
			for( IGame game : games )
			{
				scores.put(game.getHomeTeam(), game.getHomeScore());
				scores.put(game.getAwayTeam(), game.getAwayScore());
			}

			if( scores.isEmpty() == false )
			{
				List<ITeam> teams = new ArrayList<ITeam>(scores.keySet());

				Collections.sort(teams, ITeam.COMPARE_ABBR);

				for( ITeam team : teams )
				{
					buf.append(team.getAbbr())
						.append('=')
						.append(scores.get(team))
						.append(',');
				}

				buf.setLength(buf.length() - 1);
			}

			writer.println(buf.toString());

			List<? extends IPoolEntry> entries = this.poolService
				.findEntriesForWeek(week);

			for( IPoolEntry entry : entries )
			{
				buf.setLength(0);

				buf.append(entry.getPlayer().getUsername()).append(';');
				buf.append(entry.getTiebreaker()).append(';');

				Map<Integer, ITeam> teamsByRank = new HashMap<Integer, ITeam>();

				List<? extends IPick> picks = entry.getPicks();
				for( IPick pick : picks )
				{
					teamsByRank.put(pick.getRanking(), pick.getTeam());
				}

				if( teamsByRank.isEmpty() == false )
				{
					List<Integer> ranks = new ArrayList<Integer>(teamsByRank.keySet());

					Collections.sort(ranks);

					for( Integer rank : ranks )
					{
						buf.append(teamsByRank.get(rank).getAbbr())
							.append('=')
							.append(rank)
							.append(',');
					}

					buf.setLength(buf.length() - 1);
				}

				writer.println(buf.toString());
			}
		}
	}
}