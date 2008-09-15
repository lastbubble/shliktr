package org.lastbubble.shliktr.impl;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.PickBase;

/**
 * @version $Id$
 */
public class PickImpl extends PickBase
{
	private IGame game;


	//-------------------------------------------------------------------------
	// Constructors
	//-------------------------------------------------------------------------

	public PickImpl( IGame game ) { this.game = game; }


	//-------------------------------------------------------------------------
	// Implements IPick
	//-------------------------------------------------------------------------

	/** @see	IPick#getGame */
	public IGame getGame() { return this.game; }
}
