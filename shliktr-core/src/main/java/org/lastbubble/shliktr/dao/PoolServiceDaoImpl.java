package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PickStats;
import org.lastbubble.shliktr.PlayerPrediction;
import org.lastbubble.shliktr.PlayerScore;
import org.lastbubble.shliktr.Winner;
import org.lastbubble.shliktr.service.PoolService;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

/**
 * @version $Id: PoolServiceDaoImpl.java 69 2007-12-06 03:54:33Z eheaton $
 */
public class PoolServiceDaoImpl implements PoolService
{
	private PoolDao dao;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public PoolServiceDaoImpl( PoolDao dao ) { this.dao = dao; }


	//-------------------------------------------------------------------------
	// Implements PoolService
	//-------------------------------------------------------------------------

	/** @see	PoolService#findWeekById */
	@Transactional(readOnly = true)
	public IWeek findWeekById( Integer id )
	{
		IWeek week = this.dao.findWeekById(id);

		// trigger fetching
		week.getGames().size();

		return week;
	}

	/** @see	PoolService#findCurrentWeek */
	@Transactional(readOnly = true)
	public IWeek findCurrentWeek()
	{
		IWeek week = this.dao.findCurrentWeek();

		// trigger fetching
		week.getGames().size();

		return week;
	}

	/** @see	PoolService#acceptPicksForWeek */
	@Transactional(readOnly = true)
	public boolean acceptPicksForWeek( Integer id )
	{
		Date startOfWeek = this.dao.findStartOfWeek(id);
		return (startOfWeek != null && startOfWeek.after( new Date()));
	}

	/** @see	PoolService#findAllPlayers */
	@Transactional(readOnly = true)
	public List<? extends IPlayer> findAllPlayers()
	{
		return this.dao.findAllPlayers();
	}

	/** @see	PoolService#findPlayersForWeek */
	@Transactional(readOnly = true)
	public Set<? extends IPlayer> findPlayersForWeek( IWeek week )
	{
		return this.dao.findPlayersForWeek(week);
	}

	/** @see	PoolService#findPlayerById */
	@Transactional(readOnly = true)
	public IPlayer findPlayerById( Integer id )
	{
		return this.dao.findPlayerById(id);
	}

	/** @see	PoolService#findPlayerByUsername */
	@Transactional(readOnly = true)
	public IPlayer findPlayerByUsername( String username )
	{
		return this.dao.findPlayerByUsername(username);
	}

	/** @see	PoolService#findEntriesForWeek */
	@Transactional(readOnly = true)
	public List<? extends IPoolEntry> findEntriesForWeek( IWeek week )
	{
		List<? extends IPoolEntry> entries = this.dao.findEntriesForWeek(week);

		// trigger fetching
		for( IPoolEntry entry : entries )
		{
			entry.getPlayer();
			entry.getWeek();
			entry.getPicks().size();
		}

		return entries;
	}

	/** @see	PoolService#findScoresForWeek */
	@Transactional(readOnly = true)
	public List<PlayerScore> findScoresForWeek( IWeek week )
	{
		List<? extends IPoolEntry> entries = this.dao.findEntriesForWeek(week);

		List<PlayerScore> scores = new ArrayList<PlayerScore>(entries.size());

		for( IPoolEntry entry : entries )
		{
			scores.add( new PlayerScore(entry));
		}

		Collections.sort(scores);

		return scores;
	}

	/** @see	PoolService#findPickStatsForWeek */
	@Transactional(readOnly = false)
	public List<PickStats> findPickStatsForWeek( IWeek week )
	{
		return this.dao.findPickStatsForWeek(week);
	}

	/** @see	PoolService#findEntry */
	@Transactional(readOnly = false)
	public IPoolEntry findEntry( IWeek week, IPlayer player, boolean create )
	{
		PoolEntry entry = this.dao.findEntry(week, player);

		if( entry == null && create )
		{
			entry = PoolEntry.createForPlayer(
				this.dao.findWeekById(week.getWeekNumber()),
				this.dao.findPlayerByUsername(player.getUsername())
			);
		}

		// trigger fetching
		if( entry != null )
		{
			entry.getPlayer();
			entry.getWeek();
			entry.size();
		}

		return entry;
	}

	/** @see	PoolService#saveWeek */
	@Transactional(readOnly = false)
	public void saveWeek( IWeek week )
	{
		if( week instanceof Week )
			this.dao.persistWeek((Week) week);
	}

	/** @see	PoolService#saveEntry */
	@Transactional(readOnly = false)
	public void saveEntry( IPoolEntry entry )
	{
		if( entry instanceof PoolEntry )
			this.dao.persistEntry((PoolEntry) entry);
	}

	/** @see	PoolService#predictResults */
	@Transactional(readOnly = true)
	public Map<? extends IPlayer, PlayerPrediction> predictResults(
		IWeek week, List<Winner> winners )
	{
		if( week == null ) return Collections.EMPTY_MAP;

		List<? extends IGame> games = findWeekById(week.getWeekNumber()).getGames();

		if( winners == null || winners.size() != games.size() )
		{
			return Collections.EMPTY_MAP;
		}

		List<IGame> unfinishedGames = new ArrayList<IGame>(games.size());

		for( int i = 0; i < games.size(); i++ )
		{
			IGame game = games.get(i);
			Winner winner = winners.get(i);
			if( winner != null )
			{
				switch( winner )
				{
					case HOME:
						game.setHomeScore(1);
						game.setAwayScore(0);
						break;
					case AWAY:
						game.setHomeScore(0);
						game.setAwayScore(1);
						break;
				}
			}
			else
			{
				unfinishedGames.add(game);
			}
		}

		int unfinishedCnt = unfinishedGames.size();
		IGame[] unfinished = unfinishedGames.toArray( new IGame[unfinishedCnt]);

		Map<IPlayer, PlayerPrediction> predictions =
			new HashMap<IPlayer, PlayerPrediction>();

		List<? extends IPoolEntry> entries = this.dao.findEntriesForWeek(week);

		List<PlayerScore> playerScores = new ArrayList<PlayerScore>();

		StringBuilder outcomeBuf = new StringBuilder(unfinishedCnt);

		for( int i = 0, max = (int) Math.pow(2, unfinishedCnt); i < max; i++ )
		{
			outcomeBuf.setLength(0);
			String s = Integer.toString(i, 2);
			for( int j = s.length(); j < unfinishedCnt; j++ )
				outcomeBuf.append('0');
			outcomeBuf.append(s);

			String outcome = outcomeBuf.toString();

			for( int j = 0; j < unfinishedCnt; j++ )
			{
				IGame game = unfinished[j];
				if( outcome.charAt(j) == '1' )
				{
					game.setHomeScore(1);
					game.setAwayScore(0);
				}
				else
				{
					game.setHomeScore(0);
					game.setAwayScore(1);
				}
			}

			playerScores.clear();

			for( IPoolEntry entry : entries )
			{
				playerScores.add( new PlayerScore(entry));
			}

			Collections.sort(playerScores, new ScoreComparator());

			int winningScore = playerScores.get(0).getScore();
			if( winningScore == playerScores.get(1).getScore() )
			{
				outcome += "*";
			}

			for( PlayerScore playerScore : playerScores )
			{
				if( playerScore.getScore() != winningScore ) break;

				IPlayer winner = playerScore.getPlayer();

				PlayerPrediction prediction = predictions.get(winner);
				if( prediction == null )
				{
					prediction = new PlayerPrediction(winner, unfinishedCnt);
					predictions.put(winner, prediction);
				}

				prediction.addWinningOutcome(outcome);
			}
		}

		if( week instanceof Week )
			this.dao.refreshGamesForWeek((Week) week);

		return predictions;
	}


	//---------------------------------------------------------------------------
	// Nested top-level class ScoreComparator
	//---------------------------------------------------------------------------

	private static class ScoreComparator implements Comparator<PlayerScore>
	{
		/** Implements Comparator<PlayerScore> */
		public int compare( PlayerScore a, PlayerScore b )
		{
			return b.getScore() - a.getScore();
		}
	}
}
