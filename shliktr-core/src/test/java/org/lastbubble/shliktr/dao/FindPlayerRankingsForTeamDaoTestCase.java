package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.impl.PlayerImpl;

import java.util.*;

/**
 * @version $Id$
 */
public class FindPlayerRankingsForTeamDaoTestCase extends DaoTestCase
{
	static {
		createDatabase(createDataSetResource(
				"org/lastbubble/shliktr/dao/picks.xml"
			)
		);
	}

	private Map<IPlayer, Integer> rankings;


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testFindPickStatsForTeam()
	{
		rankings = poolDao.findPlayerRankingsForTeam(1, "min");
		assertNotNull(rankings);
		assertEquals( new Integer(1), rankings.get( new PlayerImpl("player1")));
		assertEquals( new Integer(2), rankings.get( new PlayerImpl("player3")));
	}
}