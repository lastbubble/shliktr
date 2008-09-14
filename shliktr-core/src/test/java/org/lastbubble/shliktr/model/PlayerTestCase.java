package org.lastbubble.shliktr.model;

import org.lastbubble.shliktr.impl.PlayerImpl;

import org.lastbubble.shliktr.impl.PlayerImplTestCase;

/**
 * @version $Id$
 */
public class PlayerTestCase extends PlayerImplTestCase
{
	@Override
	protected PlayerImpl newImpl() { return new Player(); }

	@Override
	protected PlayerImpl newImpl( String username )
	{
		return new Player(username);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testSetId()
	{
		Player player = new Player();

		assertNull(player.getId());

		Integer id = new Integer(23);
		player.setId(id);

		assertEquals(id, player.getId());
	}
}