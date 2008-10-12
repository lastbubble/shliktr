package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.PoolResult;

import java.util.List;

/**
 * @version $Id$
 */
public class FindResultsForWeekDaoTestCase extends DaoTestCase
{
	static {
		createDatabase(createDataSetResource(
				"org/lastbubble/shliktr/dao/results.xml"
			)
		);
	}

	private List<PoolResult> results;


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testFindResults_complete()
	{
		results = poolDao.findResultsForWeek(1);
		assertNotNull(results);
		assertEquals(3, results.size());
		assertEquals("player3", results.get(0).getPlayer().getUsername());
		assertEquals("player2", results.get(1).getPlayer().getUsername());
		assertEquals("player1", results.get(2).getPlayer().getUsername());
	}

	public void testFindResults_incomplete()
	{
		results = poolDao.findResultsForWeek(2);
		assertNotNull(results);
		assertEquals(4, results.size());
		assertEquals("player3", results.get(0).getPlayer().getUsername());
		assertEquals("player1", results.get(1).getPlayer().getUsername());
		assertEquals("player2", results.get(2).getPlayer().getUsername());
		assertEquals("player4", results.get(3).getPlayer().getUsername());
	}

	public void testFindResults_notExist()
	{
		results = poolDao.findResultsForWeek(3);
		assertNotNull(results);
		assertTrue(results.isEmpty());
	}
}