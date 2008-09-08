package org.lastbubble.shliktr.service;

import org.lastbubble.shliktr.model.Game;
import org.lastbubble.shliktr.model.Picks;
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

	void makePersistentWeek( Week week );

	List<Player> findAllPlayers();

	Set<Player> findPlayersForWeek( Week week );

	Player findPlayerById( Integer id );

	Player findPlayerByName( String name );

	List<Picks> findPicksForWeek( Week week );

	List<PlayerScore> findScoresForWeek( Week week );

	Picks findPicksForPlayer( Week week, Player player, boolean create );

	void makePersistentPicks( Picks picks );

	Map<Player, PlayerPrediction> predictResults(
		Week week, List<Winner> winners );

	Map<String, List<Integer>> getPickStats( Week week );

	void closeConnection();

}	// End of PoolService
