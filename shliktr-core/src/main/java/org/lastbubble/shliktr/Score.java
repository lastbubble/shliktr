package org.lastbubble.shliktr;

import javax.persistence.Embeddable;

/**
 * @version $Id$
 */
@Embeddable
public class Score implements Comparable<Score>
{
	private int points;
	private int gamesWon;
	private int gamesLost;
	private int pointsLost;


	//-------------------------------------------------------------------------
	// Constructors
	//-------------------------------------------------------------------------

	public Score() { }

	public Score( int points, int gamesWon, int gamesLost, int pointsLost )
	{
		this.points = points;
		this.gamesWon = gamesWon;
		this.gamesLost = gamesLost;
		this.pointsLost = pointsLost;
	}


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public int getPoints() { return this.points; }
	public void setPoints( int n ) { this.points = n; }

	public int getGamesWon() { return this.gamesWon; }
	public void setGamesWon( int n ) { this.gamesWon = n; }

	public int getGamesLost() { return this.gamesLost; }
	public void setGamesLost( int n ) { this.gamesLost = n; }

	public int getPointsLost() { return this.pointsLost; }
	public void setPointsLost( int n ) { this.pointsLost = n; }


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public void clear()
	{
		this.points = 0;
		this.gamesWon = 0;
		this.gamesLost = 0;
		this.pointsLost = 0;
	}

	public void add( IPick pick )
	{
		if( pick.isCorrect() )
		{
			this.points += pick.getRanking();
			this.gamesWon++;
		}
		else
		{
			this.pointsLost += pick.getRanking();
			this.gamesLost++;
		}
	}


	//-------------------------------------------------------------------------
	// Implements Comparable<Score>
	//-------------------------------------------------------------------------

	/** @see	Comparable#compareTo */
	public int compareTo( Score score )
	{
		return score.getPoints() - getPoints();
	}
}