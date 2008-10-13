package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolAccount;

/**
 * @version $Id$
 */
public class PoolAccount implements IPoolAccount, Comparable<PoolAccount>
{
	private IPlayer player;

	private int points;


	//---------------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------------

	public PoolAccount( IPlayer player )
	{
		this.player = player;
	}

	public PoolAccount( IPlayer player, int points )
	{
		this.player = player;
		this.points = points;
	}


	//---------------------------------------------------------------------------
	// Implements IPoolAccount
	//---------------------------------------------------------------------------

	/** @see	IPoolAccount#getPlayer */
	public IPlayer getPlayer() { return this.player; }

	/** @see	IPoolAccount#getPoints */
	public int getPoints() { return this.points; }


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	void update( int delta ) { this.points += delta; }


	//---------------------------------------------------------------------------
	// Implements Comparable<PoolAccount>
	//---------------------------------------------------------------------------

	/** @see	Comparable#compareTo */
	public int compareTo( PoolAccount account )
	{
		int delta = account.getPoints() - getPoints();
		if( delta != 0 ) return delta;
		return getPlayer().getName().compareTo(account.getPlayer().getName());
	}
}