package org.lastbubble.shliktr.dao;

import static org.lastbubble.shliktr.Winner.*;
import static org.lastbubble.shliktr.dao.TestHelper.*;

import java.util.*;

import junit.framework.TestCase;

/**
 * @version $Id$
 */
public class GameTestCase extends TestCase
{
	private static Team homeTeam = new Team("abc");

	private static Team awayTeam = new Team("def");

	private Game game;


	//---------------------------------------------------------------------------
	// Setup
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		game = newGame(homeTeam, awayTeam);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testCreate()
	{
		game = new Game();
		assertNull(game.getHomeTeam());
		assertNull(game.getAwayTeam());
		assertEquals(0, game.getAwayScore());
		assertEquals(0, game.getHomeScore());
		assertNull(game.getPlayedOn());
		assertFalse(game.isComplete());
	}

	public void testSetWeek()
	{
		Week week = newWeek(1);
		game.setWeek(week);
		assertSame(week, game.getWeek());
	}

	public void testSetHomeTeam()
	{
		game.setHomeTeam(homeTeam);
		assertSame(homeTeam, game.getHomeTeam());
	}

	public void testSetAwayTeam()
	{
		game.setAwayTeam(awayTeam);
		assertSame(awayTeam, game.getAwayTeam());
	}

	public void testSetHomeScore()
	{
		int score = 23;
		game.setHomeScore(score);
		assertEquals(score, game.getHomeScore());
	}

	public void testSetAwayScore()
	{
		int score = 23;
		game.setAwayScore(score);
		assertEquals(score, game.getAwayScore());
	}

	public void testSetPlayedOn()
	{
		Date date = new Date();
		game.setPlayedOn(date);
		assertEquals(date, game.getPlayedOn());
	}

	public void testSetComplete()
	{
		boolean complete = !game.isComplete();
		game.setComplete(complete);
		assertEquals(complete, game.isComplete());
	}

	public void testGetWinner()
	{
		assertNull(game.getWinner());

		game.setHomeScore(1);
		game.setAwayScore(0);
		assertSame(HOME, game.getWinner());

		game.setHomeScore(0);
		game.setAwayScore(1);
		assertSame(AWAY, game.getWinner());

		game.setHomeScore(1);
		game.setAwayScore(1);
		assertNull(game.getWinner());
	}

	public void testEquals()
	{
		assertEquals(game, game);

		assertEquals(game, newGame(homeTeam, awayTeam));
		assertFalse(game.equals(newGame(awayTeam, homeTeam)));
		assertFalse(game.equals(newGame(homeTeam, null)));
		assertFalse(game.equals(newGame(null, awayTeam)));
		assertFalse(game.equals(homeTeam));
	}

	public void testHashCode()
	{
		Set<Game> games = new HashSet<Game>();
		games.add(newGame(homeTeam, awayTeam));
		games.add(newGame(homeTeam, awayTeam));

		assertEquals(1, games.size());

		games.add(newGame(homeTeam, null));
		assertEquals(2, games.size());

		games.add(newGame(null, awayTeam));
		assertEquals(3, games.size());
	}

	public void testToString()
	{
		String s = game.toString();
		assertTrue(s.indexOf(awayTeam+" at "+homeTeam) > -1);
	}
}