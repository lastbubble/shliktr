package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.model.*;
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

	@Transactional(readOnly = true)
	public Week findWeekById( Integer id )
	{
		Week week = this.dao.findWeekById(id);

		// trigger fetching
		week.getGames().size();

		return week;
	}

	@Transactional(readOnly = true)
	public Week findCurrentWeek()
	{
		Week week = this.dao.findCurrentWeek();

		// trigger fetching
		week.getGames().size();

		return week;
	}

	@Transactional(readOnly = true)
	public boolean acceptPicksForWeek( Integer id )
	{
		Date startOfWeek = this.dao.findStartOfWeek(id);
		return (startOfWeek != null && startOfWeek.after( new Date()));
	}

	@Transactional(readOnly = true)
	public List<Player> findAllPlayers()
	{
		return this.dao.findAllPlayers();
	}

	@Transactional(readOnly = true)
	public Set<Player> findPlayersForWeek( IWeek week )
	{
		return this.dao.findPlayersForWeek(week);
	}

	@Transactional(readOnly = true)
	public Player findPlayerById( Integer id )
	{
		Player player = this.dao.findPlayerById(id);

		return player;
	}

	@Transactional(readOnly = true)
	public Player findPlayerByName( String name )
	{
		Player player = this.dao.findPlayerByName(name);

		return player;
	}

	@Transactional(readOnly = true)
	public IPlayer findPlayerByUsername( String username )
	{
		return this.dao.findPlayerByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<Picks> findPicksForWeek( IWeek week )
	{
		List<Picks> weekPicks = this.dao.findPicksForWeek(week);

		// trigger fetching
		for( Picks picks : weekPicks )
		{
			picks.getPlayer();
			picks.getWeek();
			picks.size();
		}

		return weekPicks;
	}

	@Transactional(readOnly = true)
	public List<PlayerScore> findScoresForWeek( Week week )
	{
		List<Picks> weekPicks = this.dao.findPicksForWeek(week);

		List<PlayerScore> scores = new ArrayList<PlayerScore>(weekPicks.size());

		for( Picks picks : weekPicks )
		{
			scores.add( new PlayerScore(picks));
		}

		Collections.sort(scores);

		return scores;
	}

	@Transactional(readOnly = false)
	public List<PickStats> findPickStatsForWeek( Week week )
	{
		return this.dao.findPickStatsForWeek(week);
	}

	@Transactional(readOnly = false)
	public Picks findPicksForPlayer( Week week, Player player, boolean create )
	{
		Picks picks = this.dao.findPicksForPlayer(week, player);

		if( picks == null && create )
		{
			picks = Picks.createForPlayer(week, player);
		}

		// trigger fetching
		if( picks != null )
		{
			picks.getPlayer();
			picks.getWeek();
			picks.size();
		}

		return picks;
	}

	@Transactional(readOnly = false)
	public Picks findEntry( IWeek week, IPlayer player, boolean create )
	{
		Picks picks = this.dao.findPicksForPlayer(week, player);

		if( picks == null && create )
		{
			picks = Picks.createForPlayer(
				this.dao.findWeekById(week.getWeekNumber()),
				this.dao.findPlayerByUsername(player.getUsername())
			);
		}

		// trigger fetching
		if( picks != null )
		{
			picks.getPlayer();
			picks.getWeek();
			picks.size();
		}

		return picks;
	}

	@Transactional(readOnly = false)
	public void makePersistentWeek( IWeek week )
	{
		if( week instanceof Week )
			this.dao.makePersistentWeek((Week) week);
	}

	@Transactional(readOnly = false)
	public void makePersistentPicks( Picks picks )
	{
		this.dao.makePersistentPicks(picks);
	}

	@Transactional(readOnly = false)
	public void saveEntry( IPoolEntry entry )
	{
		if( entry instanceof Picks )
			this.dao.makePersistentPicks((Picks) entry);
	}

	@Transactional(readOnly = true)
	public Map<Player, PlayerPrediction> predictResults(
		Week week, List<Winner> winners )
	{
		if( week == null ) return Collections.EMPTY_MAP;

		List<Game> games = findWeekById(week.getId()).getGames();

		if( winners == null || winners.size() != games.size() )
		{
			return Collections.EMPTY_MAP;
		}

		List<Game> unfinishedGames = new ArrayList<Game>(games.size());

		for( int i = 0; i < games.size(); i++ )
		{
			Game game = games.get(i);
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
		Game[] unfinished = unfinishedGames.toArray( new Game[unfinishedCnt]);

		Map<Player, PlayerPrediction> predictions =
			new HashMap<Player, PlayerPrediction>();

		List<Picks> picksForWeek = this.dao.findPicksForWeek(week);

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
				Game game = unfinished[j];
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

			for( Picks picks : picksForWeek )
			{
				playerScores.add( new PlayerScore(picks));
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

				Player winner = playerScore.getPlayer();

				PlayerPrediction prediction = predictions.get(winner);
				if( prediction == null )
				{
					prediction = new PlayerPrediction(winner, unfinishedCnt);
					predictions.put(winner, prediction);
				}

				prediction.addWinningOutcome(outcome);
			}
		}

		this.dao.refreshGamesForWeek(week);

		return predictions;
	}

	public void closeConnection() { }


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

}	// End of PoolServiceDaoImpl
