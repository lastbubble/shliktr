package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPlayer;

import java.util.*;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class PoolAccountTestCase extends TestCase
{
	private PoolAccount account;

	private IPlayer player;

	private int points;


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		player = createMock(IPlayer.class);

		account = new PoolAccount(player);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testProperties()
	{
		assertSame(player, account.getPlayer());
		assertEquals(0, account.getPoints());
	}

	public void testUpdate()
	{
		account.update(1);
		account.update(4);
		account.update(-2);
		assertEquals(3, account.getPoints());
	}

	public void testCompare()
	{
		PoolAccount account1 = new PoolAccount(player);
		account1.update(1);

		PoolAccount account2 = new PoolAccount(player);
		account2.update(3);

		PoolAccount account3 = new PoolAccount(player);
		account3.update(2);

		List<PoolAccount> accounts = new ArrayList<PoolAccount>(Arrays.asList(
				account1, account2, account3
			)
		);

		Collections.sort(accounts);
		assertEquals(Arrays.asList(account2, account3, account1), accounts);
	}
}