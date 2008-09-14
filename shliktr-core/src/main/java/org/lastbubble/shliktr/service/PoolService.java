package org.lastbubble.shliktr.service;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;

import org.lastbubble.shliktr.model.Game;
import org.lastbubble.shliktr.model.Picks;
import org.lastbubble.shliktr.model.PickStats;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.PlayerPrediction;
import org.lastbubble.shliktr.model.PlayerScore;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.model.Winner;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version	$Id$
 */
public interface PoolService
{
	Week findWeekById( Integer id );

	Week findCurrentWeek();

	boolean acceptPicksForWeek( Integer id );

	void makePersistentWeek( IWeek week );

	List<Player> findAllPlayers();

	Set<Player> findPlayersForWeek( IWeek week );

	Player findPlayerById( Integer id );

	Player findPlayerByName( String name );

	IPlayer findPlayerByUsername( String username );

	List<Picks> findPicksForWeek( IWeek week );

	List<PlayerScore> findScoresForWeek( Week week );

	List<PickStats> findPickStatsForWeek( Week week );

	Picks findPicksForPlayer( Week week, Player player, boolean create );

	IPoolEntry findEntry( IWeek week, IPlayer player, boolean create );

	void makePersistentPicks( Picks picks );

	void saveEntry( IPoolEntry entry );

	Map<Player, PlayerPrediction> predictResults(
		Week week, List<Winner> winners );

	void closeConnection();

}	// End of PoolService
