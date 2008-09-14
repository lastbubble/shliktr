package org.lastbubble.shliktr.impl;

import org.lastbubble.shliktr.IPlayer;

import java.util.*;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class PlayerImplTestCase extends TestCase
{
	private PlayerImpl player;


	//---------------------------------------------------------------------------
	// Template methods
	//---------------------------------------------------------------------------

	protected PlayerImpl newImpl() { return new PlayerImpl(); }

	protected PlayerImpl newImpl( String abbr ) { return new PlayerImpl(abbr); }


	//---------------------------------------------------------------------------
	// Setup
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		player = newImpl("user");
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testCreate()
	{
		player = newImpl();
		assertNull(player.getUsername());
		assertNull(player.getName());
	}

	public void testCreateWithUsername()
	{
		String username = "user";
		assertEquals(username, newImpl(username).getUsername());
	}

	public void testCreateWithUsername_illegal()
	{
		assertCreateUsernameIllegal(null, "username cannot be null");
		assertCreateUsernameIllegal("", "username cannot be empty");
		assertCreateUsernameIllegal("   ",
			"cannot change username to empty string");
		assertCreateUsernameIllegal("abcdefghijklmnopq",
			"username must be 16 chars or less");
	}

	protected void assertCreateUsernameIllegal( String username, String reason )
	{
		try {
			player = newImpl(username);
			fail("create for "+username+" should throw exception: "+reason);
		} catch( Throwable t ) {
			assertTrue("create for "+username+" should throw IllegalArgumentException",
				(t instanceof IllegalArgumentException));
		}
	}

	public void testSetUsername()
	{
		String username = "user";

		player = newImpl();
		player.setUsername(username);

		assertEquals(username, player.getUsername());
	}

	public void testSetUsername_illegal()
	{
		player = newImpl("user");

		player.setUsername("user");

		assertSetUsernameIllegal(player, "user2", "cannot change username");
		assertSetUsernameIllegal(player, null, "cannot change username to null");
		assertSetUsernameIllegal(player, "",
			"cannot change username to empty string");
		assertSetUsernameIllegal(player, "    ",
			"cannot change username to empty string");
		assertSetUsernameIllegal(newImpl(), "abcdefghijklmnopq",
		 	"username must be 3 chars or less");
	}

	protected void assertSetUsernameIllegal(
		PlayerImpl player, String username, String reason )
	{
		try {
			player.setUsername(username);
			fail("setUsername("+username+") should throw exception: "+reason);
		} catch( Throwable t ) {
			assertTrue("setUsername("+username+") should throw IllegalArgumentException",
				(t instanceof IllegalArgumentException));
		}
	}

	public void testSetName()
	{
		String name = "name";

		player = newImpl();
		player.setName(name);

		assertEquals(name, player.getName());
	}

	public void testSetPassword()
	{
		String password = "password";

		player = newImpl();
		player.setPassword(password);

		assertEquals(password, player.getPassword());
	}

	public void testSetEmailAddress()
	{
		String email = "email";

		player = newImpl();
		player.setEmailAddress(email);

		assertEquals(email, player.getEmailAddress());
	}

	public void testSetActive()
	{
		player = newImpl();

		boolean active = !player.isActive();
		player.setActive(active);

		assertEquals(active, player.isActive());
	}

	public void testCompareTo()
	{
		player = newImpl("abc");

		player.setName(null);

		assertTrue(player.compareTo(null) == 0);

		PlayerImpl player2 = newImpl("def");
		player.setName(null);

		assertTrue(player.compareTo(player2) == 0);
		assertTrue(player2.compareTo(player) == 0);

		player2.setName("def");
		assertTrue(player.compareTo(player2) > 0);
		assertTrue(player2.compareTo(player) < 0);

		player.setName("abc");
		assertTrue(player.compareTo(player2) < 0);
		assertTrue(player2.compareTo(player) > 0);
	}

	public void testEquals()
	{
		String username = "user";
		PlayerImpl player = newImpl(username);

		assertEquals(player, player);
		assertEquals(player, newImpl(username));
		assertFalse(player.equals(newImpl("user2")));

		IPlayer mock = createMock(IPlayer.class);
		expect(mock.getUsername()).andReturn(username);
		replay(mock);

		assertEquals(player, mock);

		assertFalse(player.equals(username));
	}

	public void testHashCode()
	{
		String username = "user";

		Set<PlayerImpl> players = new HashSet<PlayerImpl>();
		players.add(newImpl(username));
		players.add(newImpl(username));

		assertEquals(1, players.size());
	}

	public void testToString()
	{
		String username = "user";

		String s = newImpl(username).toString();
		assertTrue(s.indexOf("username="+username) > -1);
	}
}