package org.lastbubble.shliktr;

import java.util.List;

/**
 * Computes a player's score for the current week, as well as how close their
 * tiebreaker guess was to the actual result.
 *
 * @version $Id$
 */
public class PlayerScore implements Comparable
{
	private IPoolEntry entry;

	private int score;
	private String record;
	private int lost;
	private int remaining;
	private int tiebreakerDiff;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public PlayerScore( IPoolEntry entry )
	{
		this.entry = entry;

		int wins = 0;
		int losses = 0;

		List<? extends IPick> picks = this.entry.getPicks();

		for( IPick pick : picks )
		{
			int ranking = pick.getRanking();

			if( pick.getGame().getWinner() != null )
			{
				if( pick.isCorrect() )
				{
					this.score += ranking;
					wins++;
				}
				else
				{
					this.lost -= ranking;
					losses++;
				}
			}
			else
			{
				this.remaining += ranking;
			}
		}

		this.record = String.valueOf(wins)+"-"+String.valueOf(losses);

		this.tiebreakerDiff = Math.abs(this.entry.getTiebreaker() -
			this.entry.getWeek().getTiebreakerAnswer());
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public IPlayer getPlayer() { return this.entry.getPlayer(); }

	public int getScore() { return this.score; }

	public String getRecord() { return this.record; }

	public int getLost() { return this.lost; }

	public int getRemaining() { return this.remaining; }

	public int getTiebreaker() { return this.entry.getTiebreaker(); }

	public int getTiebreakerDiff() { return this.tiebreakerDiff; }

	/**
	 * Implements Comparable (included checking tiebreaker for ties).
	 */
	public int compareTo( Object o )
	{
		PlayerScore result = (PlayerScore) o;

		int delta = result.getScore() - getScore();
		if( delta != 0 )
			return delta;

		// same score, compare tiebreaker
		return (getTiebreakerDiff() - result.getTiebreakerDiff());
	}

	public String toString()
	{
		return new StringBuilder()
			.append(getPlayer())
			.append(" had ")
			.append(getScore())
			.append(" points ")
			.append('(')
			.append(getRecord())
			.append(')')
			.toString();
	}
}