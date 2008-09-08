package org.lastbubble.shliktr.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version $Id$
 */
public class FindStartOfWeekDaoTestCase extends DaoTestCase
{
	static {
		createDatabase(createDataSetResource(
				"org/lastbubble/shliktr/dao/test.xml"
			)
		);
	}

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testFindStartOfWeek()
	{
		assertStartOfWeekEquals("2008-12-21", 1);
		assertStartOfWeekEquals("2008-11-01", 2);
	}

	public void testFindStartOfWeek_notExist()
	{
		assertNull(poolDao.findStartOfWeek(23));
	}

	protected void assertStartOfWeekEquals( String sDate, Integer weekId )
	{
		Date date = poolDao.findStartOfWeek(weekId);
		assertNotNull(date);
		assertEquals(sDate, DATE_FORMAT.format(date));
	}

}