package org.lastbubble.shliktr.dao;

import static org.lastbubble.shliktr.Winner.*;
import static org.lastbubble.shliktr.dao.TestHelper.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class ValidateEntryTestCase extends TestCase
{
	private static Player player = newPlayer();

	private static List<Game> games = Arrays.asList(
		newGame(newTeam("t1"), newTeam("t2")),
		newGame(newTeam("t3"), newTeam("t4")),
		newGame(newTeam("t5"), newTeam("t5")),
		newGame(newTeam("t7"), newTeam("t8"))
		);

	private static Week week = newWeek(1, games);

	private List<Pick> picks;

	private PoolEntry entry;

	private String[] errmsg;


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		errmsg = new String[1];
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testNotEnoughPicks()
	{
		picks = Arrays.asList(newPick(games.get(0), HOME, 1));

		entry = newEntry(week, player, picks);

		assertFalse(entry.validate(errmsg));
		assertNotNull(errmsg[0]);
	}

	public void testTooManyPicks()
	{
		picks = Arrays.asList(
				newPick(games.get(0), HOME, 1),
				newPick(games.get(1), HOME, 2),
				newPick(games.get(2), HOME, 3),
				newPick(games.get(3), HOME, 4),
				newPick(games.get(0), HOME, 5)
		);

		entry = newEntry(week, player, picks);

		assertFalse(entry.validate(errmsg));
		assertNotNull(errmsg[0]);
	}

	public void testInvalidPicks()
	{
		picks = Arrays.asList(
				newPick(games.get(0), HOME, 1),
				newPick(games.get(1), HOME, 2),
				newPick(games.get(2), HOME, 2),
				newPick(games.get(3), HOME, 3)
		);

		entry = newEntry(week, player, picks);

		assertFalse(entry.validate(errmsg));
		assertNotNull(errmsg[0]);
	}

	public void testIllegalPicks()
	{
		picks = Arrays.asList(
				newPick(games.get(0), HOME, 1),
				newPick(games.get(1), HOME, 2),
				newPick(games.get(2), HOME, 3),
				newPick(games.get(3), HOME, 5)
		);

		entry = newEntry(week, player, picks);

		assertFalse(entry.validate(errmsg));
		assertNotNull(errmsg[0]);
	}

	public void testValidPicks()
	{
		picks = Arrays.asList(
				newPick(games.get(0), HOME, 1),
				newPick(games.get(1), HOME, 2),
				newPick(games.get(2), HOME, 3),
				newPick(games.get(3), HOME, 4)
		);

		entry = newEntry(week, player, picks);

		assertTrue(entry.validate(errmsg));
		assertNull(errmsg[0]);
	}
}