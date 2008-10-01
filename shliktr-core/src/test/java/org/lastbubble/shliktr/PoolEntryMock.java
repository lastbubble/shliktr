package org.lastbubble.shliktr;

import java.util.List;

/**
 * @version $Id$
 */
public class PoolEntryMock implements IPoolEntry
{
	private IWeek week;
	private IPlayer player;
	private List<? extends IPick> picks;
	private int tiebreaker;


	//---------------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------------

	public PoolEntryMock(
		IWeek week, IPlayer player, List<? extends IPick> picks, int tiebreaker )
	{
		this.week = week;
		this.player = player;
		this.picks = picks;
		this.tiebreaker = tiebreaker;
	}


	//---------------------------------------------------------------------------
	// Implements IPoolEntry
	//---------------------------------------------------------------------------

	/** @see	IPoolEntry#getWeek */
	public IWeek getWeek() { return this.week; }

	/** @see	IPoolEntry#getPlayer */
	public IPlayer getPlayer() { return this.player; }

	/** @see	IPoolEntry#getPicks */
	public List<? extends IPick> getPicks() { return this.picks; }

	/** @see	IPoolEntry#getTiebreaker */
	public int getTiebreaker() { return this.tiebreaker; }

	/** @see	IPoolEntry#setTiebreaker */
	public void setTiebreaker( int tiebreaker ) { }

	/** @see	IPoolEntry#computeScore */
	public void computeScore() { }

	/** @see	IPoolEntry#getScore */
	public int getScore() { return 0; }

	/** @see	IPoolEntry#getGamesWon */
	public int getGamesWon() { return 0; }

	/** @see	IPoolEntry#getGamesLost */
	public int getGamesLost() { return 0; }

	/** @see	IPoolEntry#getLost */
	public int getLost() { return 0; }

	/** @see	IPoolEntry#getRemaining */
	public int getRemaining() { return 0; }

	/** @see	IPoolEntry#getTiebreakerDiff */
	public int getTiebreakerDiff() { return 0; }

	/** @see	IPoolEntry#validate */
	public boolean validate( String[] errmsg ) { return true; }
}