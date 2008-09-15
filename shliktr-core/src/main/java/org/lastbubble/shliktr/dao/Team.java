package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.impl.TeamImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * An NFL team.
 *
 * @version $Id$
 */
@Entity
@Table(name = "team")
public final class Team extends TeamImpl
{
	private Integer id;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	Team() { super(); }

	Team( String abbr ) { super(abbr); }


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() { return this.id; }
	void setId( Integer n ) { this.id = n; }

	@Override
	@Column(length = 3, nullable = false, unique = true)
	public String getAbbr() { return super.getAbbr(); }

	@Override
	@Column(length = 16, nullable = false)
	public String getLocation() { return super.getLocation(); }

	@Override
	@Column(length = 16, nullable = false, unique = true)
	public String getName() { return super.getName(); }

}