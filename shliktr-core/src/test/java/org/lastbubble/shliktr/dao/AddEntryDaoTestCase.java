package org.lastbubble.shliktr.dao;

import static org.lastbubble.shliktr.Winner.*;

import java.util.List;

/**
 * @version $Id$
 */
public class AddEntryDaoTestCase extends DaoTestCase
{
	static {
		createDatabase(createDataSetResource(
				"org/lastbubble/shliktr/dao/picks.xml"
			)
		);
	}

	private Week week;
	private Player player;
	private PoolEntry entry;

	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testAddEntry()
	{
		week = poolDao.findWeekById(1);
		player = poolDao.findPlayerById(4);

		entry = new PoolEntry(week, player);

		List<Pick> picks = entry.getPicks();
		for( int i = 0, cnt = picks.size(); i < cnt; i++ )
		{
			Pick pick = picks.get(i);
			pick.setWinner(HOME);
			pick.setRanking(i + 1);
		}

		poolDao.persistEntry(entry);

		commitTransaction();
		startTransaction(false);

		PoolEntry actual = poolDao.findEntry(week, player);

		assertNotNull(actual);
		assertEquals(picks.size(), actual.getPicks().size());
	}
}