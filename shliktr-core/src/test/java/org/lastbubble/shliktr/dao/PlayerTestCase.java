package org.lastbubble.shliktr.dao;

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
}