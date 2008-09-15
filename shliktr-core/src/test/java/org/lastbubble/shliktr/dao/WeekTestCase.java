package org.lastbubble.shliktr.dao;

import static org.lastbubble.shliktr.dao.TestHelper.*;

import java.util.*;

import junit.framework.TestCase;

/**
 * @version $Id$
 */
public class WeekTestCase extends TestCase
{
	private Week week;


	//---------------------------------------------------------------------------
	// Setup
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		week = newWeek(1);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testCreate()
	{
		week = new Week();
		assertNull(week.getId());
		assertEquals(0, week.getWeekNumber());
		assertNull(week.getTiebreaker());
		assertEquals(0, week.getTiebreakerAnswer());
		assertEquals(Collections.EMPTY_LIST, week.getGames());
	}

	public void testGetWeekNumber()
	{
		assertEquals( new Integer(1), week.getId());
		assertEquals(1, week.getWeekNumber());
	}

	public void testSetTiebreaker()
	{
		String tiebreaker = "test";
		week.setTiebreaker(tiebreaker);
		assertEquals(tiebreaker, week.getTiebreaker());
	}

	public void testSetTiebreakerAnswer()
	{
		int tiebreakerAnswer = 23;
		week.setTiebreakerAnswer(tiebreakerAnswer);
		assertEquals(tiebreakerAnswer, week.getTiebreakerAnswer());
	}

	public void testEquals()
	{
		int weekNumber = 1;
		Week week = newWeek(1);

		assertEquals(week, week);
		assertEquals(week, newWeek(weekNumber));
		assertFalse(week.equals(newWeek(weekNumber + 1)));
		assertFalse(week.equals( new Integer(weekNumber)));
	}

	public void testHashCode()
	{
		int weekNumber = 1;

		Set<Week> weeks = new HashSet<Week>();
		weeks.add(newWeek(weekNumber));
		weeks.add(newWeek(weekNumber));
		assertEquals(1, weeks.size());

		weeks.add(newWeek(weekNumber + 1));
		assertEquals(2, weeks.size());
	}

	public void testToString()
	{
		int weekNumber = 1;
		String s = newWeek(weekNumber).toString();
		assertTrue(s.indexOf("weekNumber="+weekNumber) > -1);
	}
}