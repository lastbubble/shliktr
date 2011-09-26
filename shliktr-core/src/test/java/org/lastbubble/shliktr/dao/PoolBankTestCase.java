package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPoolAccount;
import org.lastbubble.shliktr.PoolResult;
import org.lastbubble.shliktr.Score;

import java.util.*;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class PoolBankTestCase extends TestCase
{
	private static Player[] PLAYERS = new Player[] {
		null,
		new Player("player1", "A"),
		new Player("player2", "B"),
		new Player("player3", "C"),
		new Player("player4", "D"),
		new Player("player5", "E"),
	};

	private PoolBank bank;


	//---------------------------------------------------------------------------
	// Setup methods
	//---------------------------------------------------------------------------

	protected PoolResult newResult( int week, Player player, int points )
	{
		return new PoolResult(week, player, new Score(points, 0, 0, 0), 0, 0, 0);
	}


	//---------------------------------------------------------------------------
	// Custom asserts
	//---------------------------------------------------------------------------

	protected void assertAccountEquals(
		IPoolAccount account, Player player, int points )
	{
		assertEquals(player, account.getPlayer());
		assertEquals(points, account.getPoints());
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testOneWeek()
	{
		List<PoolResult> results = Arrays.asList(
			newResult(1, PLAYERS[1], 1),
			newResult(1, PLAYERS[2], 2),
			newResult(1, PLAYERS[3], 3),
			newResult(1, PLAYERS[4], 4)
		);

		bank = new PoolBank(results);

		List<? extends IPoolAccount> accounts = bank.getAccounts();
		assertEquals(results.size(), accounts.size());
		assertAccountEquals(accounts.get(0), PLAYERS[4], 900);
		assertAccountEquals(accounts.get(1), PLAYERS[3], -100);
		assertAccountEquals(accounts.get(2), PLAYERS[1], -500);
		assertAccountEquals(accounts.get(3), PLAYERS[2], -500);

		assertEquals(200, bank.getReserve());
	}

	public void testTwoWeeks()
	{
		List<PoolResult> results = Arrays.asList(
			newResult(1, PLAYERS[1], 1),
			newResult(1, PLAYERS[2], 2),
			newResult(1, PLAYERS[3], 3),
			newResult(1, PLAYERS[4], 4),
			newResult(2, PLAYERS[1], 1),
			newResult(2, PLAYERS[2], 2),
			newResult(2, PLAYERS[3], 3),
			newResult(2, PLAYERS[4], 4),
			newResult(2, PLAYERS[5], 5)
		);

		bank = new PoolBank(results);

		List<? extends IPoolAccount> accounts = bank.getAccounts();
		assertEquals(5, accounts.size());
		assertAccountEquals(accounts.get(0), PLAYERS[5], 1250);
		assertAccountEquals(accounts.get(1), PLAYERS[4], 900);
		assertAccountEquals(accounts.get(2), PLAYERS[3], -600);
		assertAccountEquals(accounts.get(3), PLAYERS[1], -1000);
		assertAccountEquals(accounts.get(4), PLAYERS[2], -1000);

		assertEquals(450, bank.getReserve());
	}

	public void testTieForFirst()
	{
		List<PoolResult> results = Arrays.asList(
			newResult(1, PLAYERS[1], 1),
			newResult(1, PLAYERS[2], 2),
			newResult(1, PLAYERS[3], 4),
			newResult(1, PLAYERS[4], 4)
		);

		bank = new PoolBank(results);

		List<? extends IPoolAccount> accounts = bank.getAccounts();
		assertEquals(results.size(), accounts.size());
		assertAccountEquals(accounts.get(0), PLAYERS[3], 400);
		assertAccountEquals(accounts.get(1), PLAYERS[4], 400);
		assertAccountEquals(accounts.get(2), PLAYERS[1], -500);
		assertAccountEquals(accounts.get(3), PLAYERS[2], -500);

		assertEquals(200, bank.getReserve());
	}
}