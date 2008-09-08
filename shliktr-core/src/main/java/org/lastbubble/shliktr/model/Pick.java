package org.lastbubble.shliktr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A player's pick for an NFL game. Stores the game itself, as well as the
 * winner predicted by the player and the player's ranking for the game.
 *
 * @version $Id$
 */
@Entity
@Table(name = "pick")
public class Pick
{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Game game;

	@Enumerated(EnumType.STRING)
	@Column(length = 4)
	private Winner winner;

	private int ranking;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public Pick() { }

	public Pick( Game game ) { this.game = game; }

	public Pick( Game game, Winner winner, int ranking )
	{
		this.game = game;
		this.winner = winner;
		this.ranking = ranking;
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public Integer getId() { return this.id; }

	void setId( Integer n ) { this.id = n; }

	public Game getGame() { return this.game; }

	void setGame( Game g ) { this.game = g; }

	public Winner getWinner() { return this.winner; }

	public void setWinner( Winner winner ) { this.winner = winner; }

	@Transient
	public Team getTeam()
	{
		Winner winner = getWinner();
		if( winner == Winner.HOME )
			return getGame().getHomeTeam();
		if( winner == Winner.AWAY )
			return getGame().getAwayTeam();
		return null;
	}

	public int getRanking() { return this.ranking; }

	public void setRanking( int n ) { this.ranking = n; }

	/**
	 * @return	whether the pick exists in the database.
	 */
	public boolean isComplete() { return (getId() != null); }

	/** @return	whether the pick is correct. */
	public boolean isCorrect()
	{
		Winner winner = getWinner();
		return (winner != null && winner == getGame().getWinner());
	}

	/** @return the score for the pick, based on the game's outcome. */
	public int getScore() { return isCorrect() ? this.ranking : 0; }

	public int hashCode()
	{
		return getGame().hashCode() ^ getWinner().hashCode() ^ getRanking();
	}

	public boolean equals( Object obj )
	{
		if(! (obj instanceof Pick) ) return false;

		Pick p = (Pick) obj;

		return (getGame().equals(p.getGame()) &&
				getWinner().equals(p.getWinner()) &&
				getRanking() == p.getRanking());
	}

	/**
	 * @return	a String representation of the pick.
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		Game game = getGame();
		Winner winner = getWinner();

		if( winner == Winner.HOME )
			buf.append(game.getHomeTeam());
		else if( winner == Winner.AWAY )
			buf.append(game.getAwayTeam());
		else
			buf.append("<none>");
		buf.append(" for ");
		buf.append(getRanking());

		return buf.toString();
	}

}	// End of Pick