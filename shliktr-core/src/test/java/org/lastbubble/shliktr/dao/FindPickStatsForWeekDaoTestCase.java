package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.PickStats;

import java.util.*;

/**
 * @version $Id$
 */
public class FindPickStatsForWeekDaoTestCase extends DaoTestCase
{
	static {
		createDatabase(createDataSetResource(
				"org/lastbubble/shliktr/dao/picks.xml"
			)
		);
	}

	private Week week;


	//---------------------------------------------------------------------------
	// DaoTestCase methods
	//---------------------------------------------------------------------------

	@Override
	protected void onSetUp()
	{
		week = poolDao.findWeekById(1);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testFindPicksStatsForWeek()
	{
		List<PickStats> statsList = poolDao.findPickStatsForWeek(week);

		assertEquals(4, statsList.size());
		assertStatsEquals(statsList.get(0),
			"min", Arrays.asList( new Integer(2), new Integer(1)));
		assertStatsEquals(statsList.get(1),
			"chi", Arrays.asList( new Integer(1), new Integer(1)));
		assertStatsEquals(statsList.get(2), "det", Arrays.asList( new Integer(2)));
		assertStatsEquals(statsList.get(3), "gb", Arrays.asList( new Integer(1)));
	}

	protected void assertStatsEquals( PickStats stats,
		String abbr, List<Integer> rankings )
	{
		assertEquals(abbr, stats.getTeam().getAbbr());
		assertEquals(rankings, stats.getRankings());
	}
}