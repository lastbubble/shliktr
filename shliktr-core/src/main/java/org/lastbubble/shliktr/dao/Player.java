package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.impl.PlayerImpl;

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
public final class Player extends PlayerImpl implements Serializable
{
	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	Player() { super(); }

	Player( String username ) { super(username); }

	Player( String username, String name )
	{
		super(username);
		setName(name);
	}

	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() { return super.getId(); }

	@Column(length = 16, nullable = false, unique = true)
	public String getUsername() { return super.getUsername(); }

	@Column(length = 32, nullable = false, unique = true)
	public String getName() { return super.getName(); }

	@Column(length = 32)
	public String getPassword() { return super.getPassword(); }

	@Column(name = "email")
	public String getEmailAddress() { return super.getEmailAddress(); }

	@Column(name = "active")
	public boolean isActive() { return super.isActive(); }
}