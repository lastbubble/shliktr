package org.lastbubble.shliktr.model;

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
public class Game
{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "home_team_id", nullable = false)
	private Team homeTeam;

	@ManyToOne
	@JoinColumn(name = "away_team_id", nullable = false)
	private Team awayTeam;

	@Column(name = "home_score")
	private int homeScore;

	@Column(name = "away_score")
	private int awayScore;

	@Column(name="played_on", nullable = false)
	private Date playedOn;

	@Transient
	private boolean complete;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public Game() { }


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public Integer getId() { return this.id; }

	void setId( Integer n ) { this.id = n; }

	public Team getHomeTeam() { return this.homeTeam; }

	void setHomeTeam( Team t ) { this.homeTeam = t; }

	public Team getAwayTeam() { return this.awayTeam; }

	void setAwayTeam( Team t ) { this.awayTeam = t; }

	public Date getPlayedOn() { return this.playedOn; }

	void setPlayedOn( Date d ) { this.playedOn = d; }

	public int getHomeScore() { return this.homeScore; }

	public void setHomeScore( int n ) { this.homeScore = n; }

	public int getAwayScore() { return this.awayScore; }

	public void setAwayScore( int n ) { this.awayScore = n; }

	/**
	 * @return	whether the game's outcome has been decided and exists in the
	 *			database.
	 */
	public boolean isComplete() { return this.complete; }

	void setComplete( boolean b ) { this.complete = b; }

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

	public int hashCode()
	{
		return getHomeTeam().hashCode() ^ getAwayTeam().hashCode();
	}

	public boolean equals( Object obj )
	{
		if(! (obj instanceof Game) ) return false;

		Game g = (Game) obj;
		return (getHomeTeam().equals(g.getHomeTeam()) &&
				getAwayTeam().equals(g.getAwayTeam()) &&
				getPlayedOn().equals(g.getPlayedOn()));
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		buf.append(getAwayTeam());
		buf.append(" at ");
		buf.append(getHomeTeam());
		buf.append(" (");
		buf.append(getAwayScore());
		buf.append("-");
		buf.append(getHomeScore());
		buf.append(")");

		return buf.toString();
	}

}	// End of Game