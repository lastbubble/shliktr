package org.lastbubble.shliktr.dao;

import java.util.Date;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class AcceptPicksForWeekTestCase extends PoolServiceTestCase
{
	private static Integer weekId = new Integer(1);

	private static Date NOW = new Date();

	private Date startOfWeek;


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testAcceptPicksForWeek()
	{
		startOfWeek = new Date(NOW.getTime() + 60 * 1000);

		expect(poolDao.findStartOfWeek(weekId)).andReturn(startOfWeek);
		replay(poolDao);

		assertTrue(poolService.acceptPicksForWeek(weekId));

		verify(poolDao);
	}

	public void testAcceptPicksForWeek_expired()
	{
		startOfWeek = NOW;

		expect(poolDao.findStartOfWeek(weekId)).andReturn(startOfWeek);
		replay(poolDao);

		assertFalse(poolService.acceptPicksForWeek(weekId));

		verify(poolDao);
	}

}