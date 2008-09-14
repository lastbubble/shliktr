package org.lastbubble.shliktr.tools;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.impl.TeamImpl;
import static org.lastbubble.shliktr.model.Winner.*;
import org.lastbubble.shliktr.service.PoolService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version $Id$
 */
public class PoolLoader
{
	private static Pattern WEEK_PTN = Pattern.compile("week(\\d+);(\\d+);(.*)");

	private static Pattern TEAM_PTN = Pattern.compile("([a-z]{2,3})=(\\d+)");

	private static Pattern PLAYER_PTN = Pattern.compile("(.*);(\\d+);(.*)");

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

	public void load( BufferedReader reader )
	throws IOException
	{
		String line;
		Matcher matcher;

		int weekNumber = 0;

		while( (line = reader.readLine()) != null )
		{
			if( line.length() == 0 || line.startsWith("#") ) continue;

			matcher = WEEK_PTN.matcher(line);
			if( matcher.matches() )
			{
				weekNumber = Integer.parseInt(matcher.group(1));
				int tiebreakerAnswer = Integer.parseInt(matcher.group(2));
				String teams = matcher.group(3);

				Map<TeamImpl, Integer> scores = new HashMap<TeamImpl, Integer>();

				matcher = TEAM_PTN.matcher(teams);
				while( matcher.find() )
				{
					String abbr = matcher.group(1);
					Integer score = new Integer(matcher.group(2));

					scores.put( new TeamImpl(abbr), score);
				}

				updateWeek(weekNumber, tiebreakerAnswer, scores);
				continue;
			}

			matcher = PLAYER_PTN.matcher(line);
			if( matcher.matches() )
			{
				String username = matcher.group(1);
				int tiebreaker = Integer.parseInt(matcher.group(2));
				String teams = matcher.group(3);

				Map<TeamImpl, Integer> rankings = new HashMap<TeamImpl, Integer>();

				matcher = TEAM_PTN.matcher(teams);
				while( matcher.find() )
				{
					String abbr = matcher.group(1);
					Integer ranking = new Integer(matcher.group(2));

					rankings.put( new TeamImpl(abbr), ranking);
				}

				updateEntry(weekNumber, username, tiebreaker, rankings);
				continue;
			}
		}
	}

	protected void updateWeek(
		int weekNumber, int tiebreakerAnswer, Map<TeamImpl, Integer> scores )
	{
		IWeek week = this.poolService.findWeekById(weekNumber);

		week.setTiebreakerAnswer(tiebreakerAnswer);

		List<? extends IGame> games = week.getGames();
		for( IGame game : games )
		{
			Integer homeScore = scores.get(game.getHomeTeam());
			if( homeScore != null )
				game.setHomeScore(homeScore);

			Integer awayScore = scores.get(game.getAwayTeam());
			if( awayScore != null )
				game.setAwayScore(awayScore);
		}

		this.poolService.makePersistentWeek(week);
	}

	protected void updateEntry( int weekNumber, String username,
		int tiebreaker, Map<TeamImpl, Integer> rankings )
	{
		IWeek week = this.poolService.findWeekById(weekNumber);
		IPlayer player = this.poolService.findPlayerByUsername(username);

		IPoolEntry entry = this.poolService.findEntry(week, player, true);

		entry.setTiebreaker(tiebreaker);

		List<? extends IPick> picks = entry.getPicks();
		for( IPick pick : picks )
		{
			IGame game = pick.getGame();

			Integer ranking;
			if( (ranking = rankings.get(game.getHomeTeam())) != null )
			{
				pick.setWinner(HOME);
				pick.setRanking(ranking);
			}
			else if( (ranking = rankings.get(game.getAwayTeam())) != null )
			{
				pick.setWinner(AWAY);
				pick.setRanking(ranking);
			}
		}

		this.poolService.saveEntry(entry);
	}
}