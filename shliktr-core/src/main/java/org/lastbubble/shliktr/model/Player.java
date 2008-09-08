package org.lastbubble.shliktr.model;

import java.io.Serializable;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A player in the pool.
 *
 * @version $Id$
 */
@Entity
@Table(name = "player")
public final class Player implements Comparable<Player>, Serializable
{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(length = 16, nullable = false, unique = true)
	private String username;

	@Column(length = 32, nullable = false, unique = true)
	private String name;

	@Column(length = 32)
	private String password;

	@Column(name = "email")
	private String emailAddress;

	private boolean active;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public Player() { }

	public Player( String username )
	{
		this.username = username;
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public Integer getId() { return this.id; }

	void setId( Integer id ) { this.id = id; }

	public String getUsername() { return this.username; }

	public String getName() { return this.name; }

	public void setName( String s ) { this.name = s; }

	public String getPassword() { return this.password; }

	public String getEmailAddress() { return this.emailAddress; }

	public void setEmailAddress( String s ) { this.emailAddress = s; }

	public boolean isActive() { return this.active; }

	public int hashCode() { return getUsername().hashCode(); }

	public boolean equals( Object obj )
	{
		if(! (obj instanceof Player) ) return false;

		return ((Player) obj).getUsername().equals(getUsername());
	}

	/** @return	a String representation of the player. */
	public String toString() { return getName(); }

	/** Implements Comparable, so players can be sorted by name. */
	public int compareTo( Player p )
	{
		return getName().compareTo(p.getName());
	}

}	// End of Player