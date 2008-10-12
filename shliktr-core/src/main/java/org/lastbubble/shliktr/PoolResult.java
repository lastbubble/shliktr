package org.lastbubble.shliktr;

/**
 * @version $Id$
 */
public class PoolResult implements Comparable<PoolResult>
{
	private int week;

	private IPlayer player;

	private Score score;

	private int pointsRemaining;

	private int tiebreaker;

	private int tiebreakerDiff;


	//---------------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------------

	public PoolResult(
		int week,
		IPlayer player,
		Score score,
		int gameCnt,
		int tiebreaker,
		int tiebreakerDiff )
	{
		this.week = week;
		this.player = player;
		this.score = score;
		this.pointsRemaining = ((gameCnt * (gameCnt + 1)) / 2)
			- score.getPoints() - score.getPointsLost();
		this.tiebreaker = tiebreaker;
		this.tiebreakerDiff = tiebreakerDiff;
	}


	//---------------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------------

	public int getWeek() { return this.week; }

	public IPlayer getPlayer() { return this.player; }

	public int getPoints() { return this.score.getPoints(); }

	public int getGamesWon() { return this.score.getGamesWon(); }

	public int getGamesLost() { return this.score.getGamesLost(); }

	public int getPointsLost() { return this.score.getPointsLost(); }

	public int getPointsRemaining() { return this.pointsRemaining; }

	public int getTiebreaker() { return this.tiebreaker; }

	public int getTiebreakerDiff() { return this.tiebreakerDiff; }


	//---------------------------------------------------------------------------
	// Implements Comparable<PoolResult>
	//---------------------------------------------------------------------------

	/** @see	Comparable#compareTo */
	public int compareTo( PoolResult result )
	{
		int scoreDelta = this.score.compareTo(result.score);
		return (scoreDelta != 0) ?
			scoreDelta : getTiebreakerDiff() - result.getTiebreakerDiff();
	}
}