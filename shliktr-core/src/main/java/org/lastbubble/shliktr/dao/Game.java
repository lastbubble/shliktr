package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IGame;
import static org.lastbubble.shliktr.ShliktrUtils.*;
import org.lastbubble.shliktr.Winner;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A game between two NFL teams. Includes the game's score, and the date on
 * which the game was played.
 *
 * @version $Id$
 */
@Entity
@Table(name = "game")
public final class Game implements IGame
{
	private Integer id;

	private Week week;

	private Team homeTeam;

	private Team awayTeam;

	private int homeScore;

	private int awayScore;

	private Date playedOn;

	private boolean complete;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	Game() { }


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() { return this.id; }
	void setId( Integer n ) { this.id = n; }

	@ManyToOne
	@JoinColumn(
		name = "week_id",
		nullable = false,
		insertable = false,
		updatable = false
	)
	Week getWeek() { return this.week = week; }
	void setWeek( Week week ) { this.week = week; }

	/** @see	IGame#getHomeTeam */
	@ManyToOne
	@JoinColumn(name = "home_team_id", nullable = false)
	public Team getHomeTeam() { return this.homeTeam; }
	void setHomeTeam( Team t ) { this.homeTeam = t; }

	/** @see	IGame#getAwayTeam */
	@ManyToOne
	@JoinColumn(name = "away_team_id", nullable = false)
	public Team getAwayTeam() { return this.awayTeam; }
	void setAwayTeam( Team t ) { this.awayTeam = t; }

	/** @see	IGame#getHomeScore */
	@Column(name = "home_score")
	public int getHomeScore() { return this.homeScore; }

	/** @see	IGame#setHomeScore */
	public void setHomeScore( Integer n )
	{
		this.homeScore = (n != null) ? n : 0;
	}

	/** @see	IGame#getAwayScore */
	@Column(name = "away_score")
	public int getAwayScore() { return this.awayScore; }

	/** @see	IGame#setAwayScore */
	public void setAwayScore( Integer n )
	{
		this.awayScore = (n != null) ? n : 0;
	}

	/** @see	IGame#getPlayedOn */
	@Column(name="played_on", nullable = false)
	public Date getPlayedOn() { return this.playedOn; }
	void setPlayedOn( Date d ) { this.playedOn = d; }

	/** @see	IGame#isComplete */
	@Transient
	public boolean isComplete() { return this.complete; }
	void setComplete( boolean b ) { this.complete = b; }


	//---------------------------------------------------------------------------
	// Derived properties
	//---------------------------------------------------------------------------

	@Transient
	public Winner getWinner()
	{
		int homeScore = getHomeScore();
		int awayScore = getAwayScore();

		if( homeScore > awayScore )
		{
			return Winner.HOME;
		}
		else if( awayScore > homeScore )
		{
			return Winner.AWAY;
		}

		return null;
	}


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	public boolean equals( Object obj )
	{
		if( obj == this ) return true;

		if( obj instanceof IGame )
		{
			IGame game = (IGame) obj;
			return nullSafeEquals(this.homeTeam, game.getHomeTeam())
				&& nullSafeEquals(this.awayTeam, game.getAwayTeam());
		}

		return false;
	}

	public int hashCode()
	{
		int hashCode = 0;

		if( this.homeTeam != null )
			hashCode += this.homeTeam.hashCode();

		if( this.awayTeam != null )
			hashCode += 17 * this.awayTeam.hashCode();

		return hashCode;
	}

	public String toString()
	{
		return new StringBuilder()
			.append(getAwayTeam())
			.append(" at ")
			.append(getHomeTeam())
			.append(" (")
			.append(getAwayScore())
			.append('-')
			.append(getHomeScore())
			.append(')')
			.toString();
	}
}