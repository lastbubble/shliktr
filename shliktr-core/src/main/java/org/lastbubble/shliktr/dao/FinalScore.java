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

	private int total;

	private List<PoolResult> results;


	//---------------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------------

	public FinalScore( IPlayer player, List<PoolResult> results, int lastWeek )
	{
		this.player = player;

		this.results = new ArrayList<PoolResult>(results);
		Collections.sort(this.results);

		for( int i = 0, cnt = this.results.size(); i < cnt; i++ )
		{
			int n = this.results.get(i).getPoints();
			this.total += n;
			if( i < lastWeek ) { this.points += n; }
		}
	}


	//---------------------------------------------------------------------------
	// Implements IFinalScore
	//---------------------------------------------------------------------------

	/** @see	IFinalScore#getPlayer */
	public IPlayer getPlayer() { return this.player; }

	/** @see	IFinalScore#getPoints */
	public int getPoints() { return this.points; }

	/** @see	IFinalScore#getTotal */
	public int getTotal() { return this.total; }

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