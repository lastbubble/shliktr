package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IWeek;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @version $Id: Week.java 59 2007-11-26 05:32:22Z eheaton $
 */
@Entity
@Table(name = "week")
public final class Week implements IWeek
{
	private Integer id;

	private List<Game> games = Collections.EMPTY_LIST;

	private String tiebreaker;

	private int tiebreakerAnswer;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	Week() { }


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	@Id
	public Integer getId() { return this.id; }
	void setId( Integer n ) { this.id = n; }

	/** @see	IWeek#getWeekNumber */
	@Transient
	public int getWeekNumber() { return (this.id != null) ? this.id : 0; }

	/** @see	IWeek#getGames */
	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "week_id", nullable = false)
	public List<Game> getGames()
	{
		return Collections.unmodifiableList(this.games);
	}
	void setGames( List<Game> games ) { this.games = games; }

	@Column(name = "tiebreaker")
	public String getTiebreaker() { return this.tiebreaker; }

	/** @see	IWeek#setTiebreaker */
	public void setTiebreaker( String s ) { this.tiebreaker = s; }

	/** @see	IWeek#getTiebreakerAnswer */
	@Column(name = "tiebreaker_answer")
	public int getTiebreakerAnswer() { return this.tiebreakerAnswer; }

	/** @see	IWeek#setTiebreakerAnswer */
	public void setTiebreakerAnswer( int n ) { this.tiebreakerAnswer = n; }


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	public boolean equals( Object obj )
	{
		if( obj == this ) return true;

		if( obj instanceof Week )
		{
			return getWeekNumber() == ((Week) obj).getWeekNumber();
		}

		return false;
	}

	public int hashCode() { return getWeekNumber(); }

	public String toString()
	{
		return new StringBuilder()
			.append(getClass().getName())
			.append('[')
			.append("weekNumber=")
			.append(getWeekNumber())
			.append(']')
			.toString();
	}
}
