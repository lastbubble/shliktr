package org.lastbubble.shliktr.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @version $Id: Week.java 59 2007-11-26 05:32:22Z eheaton $
 */
@Entity
@Table(name = "week")
public class Week
{
	@Id
	private Integer id;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "week_id", nullable = false)
	private List<Game> games;

	private String tiebreaker;

	@Column(name = "tiebreaker_answer")
	private int tiebreakerAnswer;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public Week() { }

	public Week( Integer id, List<Game> games )
	{
		this.id = id;
		this.games = games;
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public Integer getId() { return this.id; }

	void setId( Integer n ) { this.id = n; }

	public List<Game> getGames()
	{
		return Collections.unmodifiableList(this.games);
	}

	void setGames( List<Game> l ) { this.games = l; }

	public int getGameCount() { return this.games.size(); }

	public Game getGameAt( int i ) { return this.games.get(i); }

	public Game findGameById( Integer gameId )
	{
		Game game = null;

		for( int i = 0, cnt = getGameCount(); i < cnt; i++ )
		{
			if( getGameAt(i).getId().equals(gameId) )
			{
				return getGameAt(i);
			}
		}

		return game;
	}

	public String getTiebreaker() { return this.tiebreaker; }

	public void setTiebreaker( String s ) { this.tiebreaker = s; }

	public int getTiebreakerAnswer() { return this.tiebreakerAnswer; }

	public void setTiebreakerAnswer( int n ) { this.tiebreakerAnswer = n; }

	public Date getStart()
	{
		Date date = new Date(Long.MAX_VALUE);

		for( int i = 0, cnt = getGameCount(); i < cnt; i++ )
		{
			Date gameDate = getGameAt(i).getPlayedOn();
			if( gameDate != null && gameDate.compareTo(date) < 0 )
			{
				date = gameDate;
			}
		}

		return date;
	}

	public int hashCode() { return getId().hashCode(); }

	public boolean equals( Object obj )
	{
		if(! (obj instanceof Week) ) return false;

		return ((Week) obj).getId().equals(getId());
	}

	/** @return	a String representation of the week. */
	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("WEEK ");
		buf.append(getId());

		return buf.toString();
	}

}	// End of Week
