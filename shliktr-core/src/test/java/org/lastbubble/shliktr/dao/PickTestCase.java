package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.Winner;
import static org.lastbubble.shliktr.Winner.*;
import static org.lastbubble.shliktr.dao.TestHelper.*;

import java.util.*;

import junit.framework.TestCase;

/**
 * @version $Id$
 */
public class PickTestCase extends TestCase
{
	private Game game;

	private Winner winner;

	private int ranking;

	private Pick pick;


	//---------------------------------------------------------------------------
	// Setup
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		game = newGame();
		winner = HOME;
		ranking = 1;

		pick = newPick(game, winner, ranking);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testCreate()
	{
		pick = new Pick();
		assertNull(pick.getId());
		assertNull(pick.getGame());
		assertNull(pick.getWinner());
		assertEquals(0, pick.getRanking());
	}

	public void testSetGame()
	{
		game = newGame();
		pick.setGame(game);
		assertSame(game, pick.getGame());
	}

	public void testSetWinner()
	{
		winner = AWAY;
		pick.setWinner(winner);
		assertSame(winner, pick.getWinner());
	}

	public void testSetRanking()
	{
		ranking = ranking + 1;
		pick.setRanking(ranking);
		assertSame(ranking, pick.getRanking());
	}
}