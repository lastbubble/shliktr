package org.lastbubble.shliktr;

import java.util.*;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class PickStatsTestCase extends TestCase
{
	private static ITeam team = createMock(ITeam.class);

	private PickStats stats;


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	protected void setUp()
	{
		stats = new PickStats(team);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testDefaults()
	{
		assertSame(team, stats.getTeam());
		assertTrue(stats.getRankings().isEmpty());
	}

	public void testAddRanking()
	{
		assertTrue(stats.getRankings().isEmpty());

		Integer ranking = new Integer(16);
		stats.addRanking(ranking);

		assertEquals(Arrays.asList(ranking), stats.getRankings());
	}

	public void testAddRanking_duplicate()
	{
		assertTrue(stats.getRankings().isEmpty());

		Integer ranking = new Integer(16);
		stats.addRanking(ranking);
		stats.addRanking(ranking);
		assertEquals(Arrays.asList(ranking, ranking), stats.getRankings());
	}

	public void testGetRankings_sorted()
	{
		assertTrue(stats.getRankings().isEmpty());

		List<Integer> expected = Arrays.asList(
			new Integer(16), new Integer(8), new Integer(1)
		);
		for( int i = expected.size() - 1; i >= 0; i-- )
		{
			stats.addRanking(expected.get(i));
		}

		assertEquals(expected, stats.getRankings());
	}

	public void testGetRankings_compareRankingsCount()
	{
		PickStats stats1 = new PickStats(team);
		stats1.addRanking(1);
		stats1.addRanking(2);

		PickStats stats2 = new PickStats(team);
		stats2.addRanking(16);

		assertTrue(stats1.compareTo(stats2) < 0);
	}

	public void testGetRankings_compareTotal()
	{
		PickStats stats1 = new PickStats(team);
		stats1.addRanking(1);

		PickStats stats2 = new PickStats(team);
		stats2.addRanking(16);

		assertTrue(stats1.compareTo(stats2) > 0);
	}
}