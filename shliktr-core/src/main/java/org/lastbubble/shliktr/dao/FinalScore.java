package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IFinalScore;
import org.lastbubble.shliktr.PoolResult;

import java.util.*;

/**
 * @version $Id$
 */
public class FinalScore implements IFinalScore, Comparable<FinalScore>
{
	private IPlayer player;

	private int points;

	private List<PoolResult> results;


	//---------------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------------

	public FinalScore( IPlayer player, List<PoolResult> results )
	{
		this.player = player;

		this.results = new ArrayList<PoolResult>(results);
		Collections.sort(this.results);

		int cnt = this.results.size();
		if( cnt > 3 ) cnt -= 3;

		this.points = 0;
		for( int i = 0; i < cnt; i++ )
		{
			this.points += this.results.get(i).getPoints();
		}
	}


	//---------------------------------------------------------------------------
	// Implements IFinalScore
	//---------------------------------------------------------------------------

	/** @see	IFinalScore#getPlayer */
	public IPlayer getPlayer() { return this.player; }

	/** @see	IFinalScore#getPoints */
	public int getPoints() { return this.points; }

	/** @see	IFinalScore#getResults */
	public List<PoolResult> getResults()
	{
		return Collections.unmodifiableList(this.results);
	}


	//---------------------------------------------------------------------------
	// Implements Comparable<FinalScore>
	//---------------------------------------------------------------------------

	/** @see	Comparable#compareTo */
	public int compareTo( FinalScore score )
	{
		int delta = score.getPoints() - getPoints();
		if( delta != 0 ) return delta;
		return getPlayer().getName().compareTo(score.getPlayer().getName());
	}
}