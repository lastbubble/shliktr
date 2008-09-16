package org.lastbubble.shliktr.impl;

import org.lastbubble.shliktr.IPlayer;

/**
 * @version $Id$
 */
public class PlayerImpl implements IPlayer, Comparable<IPlayer>
{
	private Integer id;

	private String username;

	private String name;

	private String password;

	private String email;

	private boolean active;


	//-------------------------------------------------------------------------
	// Constructors
	//-------------------------------------------------------------------------

	protected PlayerImpl() { }

	public PlayerImpl( String username )
	{
		this.username = normalizeUsername(username);
	}


	//-------------------------------------------------------------------------
	// Implements IPlayer
	//-------------------------------------------------------------------------

	/** @see	IPlayer#getId */
	public Integer getId() { return this.id; }

	protected void setId( Integer n ) { this.id = n; }

	/** @see	IPlayer#getUsername */
	public String getUsername() { return this.username; }

	protected void setUsername( String s )
	{
		String norm = normalizeUsername(s);

		if( this.username != null && this.username.equals(norm) == false )
			throw new IllegalArgumentException("Cannot change username");

		this.username = norm;
	}

	/** @see	IPlayer#getName */
	public String getName() { return this.name; }

	protected void setName( String s ) { this.name = s; }

	/** @see	IPlayer#getPassword */
	public String getPassword() { return this.password; }

	protected void setPassword( String s ) { this.password = s; }

	/** @see	IPlayer#getEmailAddress */
	public String getEmailAddress() { return this.email; }

	protected void setEmailAddress( String s ) { this.email = s; }

	/** @see	IPlayer#isActive */
	public boolean isActive() { return this.active; }

	protected void setActive( Boolean b )
	{
		this.active = (b != null) ? b : false;
	}


	//---------------------------------------------------------------------------
	// Implements Comparable<IPlayer>
	//---------------------------------------------------------------------------

	public int compareTo( IPlayer player )
	{
		String name = (player != null) ? player.getName() : null;

		if( name == null ) { return (this.name != null) ? -1 : 0; }

		return (this.name != null) ? this.name.compareTo(name) : 1;
	}


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	protected static String normalizeUsername( String s )
	{
		if( s == null )
			throw new IllegalArgumentException("username cannot be null");

		String norm = s.trim();

		if( norm.length() == 0 || norm.length() > 16 )
			throw new IllegalArgumentException("invalid username");

		return norm;
	}

	public boolean equals( Object obj )
	{
		if( obj == this ) return true;

		if( obj instanceof IPlayer )
		{
			return ((IPlayer) obj).getUsername().equals(this.username);
		}

		return false;
	}

	public int hashCode() { return this.username.hashCode(); }

	public String toString()
	{
		return new StringBuilder()
			.append(getClass().getName())
			.append('[')
			.append("username=")
			.append(getUsername())
			.append(']')
			.toString();
	}
}
