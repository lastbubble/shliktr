package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PickStats;
import org.lastbubble.shliktr.PoolResult;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version $Id: PoolDao.java 69 2007-12-06 03:54:33Z eheaton $
 */
public interface PoolDao
{
	Week findWeekById( Integer id );

	Week findCurrentWeek();

	Date findStartOfWeek( Integer id );

	List<Player> findAllPlayers();

	Set<Player> findPlayersForWeek( IWeek week );

	Player findPlayerById( Integer id );

	Player findPlayerByUsername( String username );

	List<PoolEntry> findEntriesForWeek( IWeek week );

	List<PoolResult> findResultsForWeek( int week );

	List<PoolResult> findAllResults();

	PoolEntry findEntry( IWeek week, IPlayer player );

	List<PickStats> findPickStatsForWeek( IWeek week );

	Map<IPlayer, Integer> findPlayerRankingsForTeam( int week, String team );

	void refreshGamesForWeek( Week week );

	void persistWeek( Week week );

	void persistEntry( PoolEntry entry );
}
