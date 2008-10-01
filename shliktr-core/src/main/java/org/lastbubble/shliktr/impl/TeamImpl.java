package org.lastbubble.shliktr.impl;

import org.lastbubble.shliktr.ITeam;

/**
 * @version $Id$
 */
public class TeamImpl implements ITeam, Comparable<ITeam>
{
	private String abbr;

	private String location;

	private String name;


	//-------------------------------------------------------------------------
	// Constructors
	//-------------------------------------------------------------------------

	protected TeamImpl() { }

	public TeamImpl( String abbr )
	{
		this.abbr = normalizeAbbr(abbr);
	}


	//-------------------------------------------------------------------------
	// Implements ITeam
	//-------------------------------------------------------------------------

	/** @see	ITeam#getAbbr */
	public String getAbbr() { return this.abbr; }

	protected void setAbbr( String s )
	{
		String norm = normalizeAbbr(s);

		if( this.abbr != null && this.abbr.equals(norm) == false )
			throw new IllegalArgumentException("Cannot change abbr");

		this.abbr = norm;
	}

	/** @see	ITeam#getLocation */
	public String getLocation() { return this.location; }

	protected void setLocation( String s ) { this.location = s; }

	/** @see	ITeam#getName */
	public String getName() { return this.name; }

	protected void setName( String s ) { this.name = s; }


	//---------------------------------------------------------------------------
	// Implements Comparable<IPlayer>
	//---------------------------------------------------------------------------

	public int compareTo( ITeam team )
	{
		return COMPARE_ABBR.compare(this, team);
	}


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	protected static String normalizeAbbr( String s )
	{
		if( s == null )
			throw new IllegalArgumentException("abbr cannot be null");

		String norm = s.trim();

		if( norm.length() == 0 || norm.length() > 3 )
			throw new IllegalArgumentException("invalid abbr");

		return norm;
	}

	public boolean equals( Object obj )
	{
		if( obj == this ) return true;

		if( obj instanceof ITeam )
		{
			return ((ITeam) obj).getAbbr().equals(this.abbr);
		}

		return false;
	}

	public int hashCode() { return this.abbr.hashCode(); }

	public String toString()
	{
		return new StringBuilder()
			.append(getClass().getName())
			.append('[')
			.append("abbr=")
			.append(getAbbr())
			.append(']')
			.toString();
	}
}
