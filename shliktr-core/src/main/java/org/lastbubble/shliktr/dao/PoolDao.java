package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.model.Picks;
import org.lastbubble.shliktr.model.PickStats;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.Week;

import java.util.Date;
import java.util.List;
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

	Set<Player> findPlayersForWeek( Week week );

	Player findPlayerById( Integer id );

	Player findPlayerByName( String name );

	List<Picks> findPicksForWeek( Week week );

	Picks findPicksForPlayer( Week week, Player player );

	List<PickStats> findPickStatsForWeek( Week week );

	void refreshGamesForWeek( Week week );

	void makePersistentWeek( Week week );

	void makePersistentPicks( Picks picks );

}	// End of PoolDao
