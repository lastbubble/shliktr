package org.lastbubble.shliktr.model;

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
	private Integer id;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	Player() { super(); }

	public Player( String username ) { super(username); }


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() { return this.id; }
	void setId( Integer id ) { this.id = id; }

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