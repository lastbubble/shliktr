package org.lastbubble.shliktr.service;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PickStats;
import org.lastbubble.shliktr.PlayerPrediction;
import org.lastbubble.shliktr.PoolResult;
import org.lastbubble.shliktr.Winner;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version	$Id$
 */
public interface PoolService
{
	IWeek findWeekById( Integer id );

	IWeek findCurrentWeek();

	boolean acceptPicksForWeek( Integer id );

	void saveWeek( IWeek week );

	List<? extends IPlayer> findAllPlayers();

	Set<? extends IPlayer> findPlayersForWeek( IWeek week );

	IPlayer findPlayerById( Integer id );

	IPlayer findPlayerByUsername( String username );

	List<? extends IPoolEntry> findEntriesForWeek( IWeek week );

	List<PoolResult> findResultsForWeek( int week );

	List<PickStats> findPickStatsForWeek( IWeek week );

	IPoolEntry findEntry( IWeek week, IPlayer player, boolean create );

	void saveEntry( IPoolEntry entry );

	Map<? extends IPlayer, PlayerPrediction> predictResults(
		IWeek week, List<Winner> winners );
}
