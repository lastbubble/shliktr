package org.lastbubble.shliktr.model;

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
public final class Team
{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(length = 3, nullable = false, unique = true)
	private String abbr;

	@Column(length = 16, nullable = false)
	private String location;

	@Column(length = 16, nullable = false, unique = true)
	private String name;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public Team() { }


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public Integer getId() { return this.id; }

	void setId( Integer n ) { this.id = n; }

	public String getAbbr() { return this.abbr; }

	void setAbbr( String s ) { this.abbr = s; }

	public String getLocation() { return this.location; }

	void setLocation( String s ) { this.location = s; }

	public String getName() { return this.name; }

	void setName( String s ) { this.name = s; }

	public int hashCode() { return getAbbr().hashCode(); }

	public boolean equals( Object obj )
	{
		if(! (obj instanceof Team) ) return false;

		return ((Team) obj).getAbbr().equals(getAbbr());
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("Team");
		buf.append('[');
		buf.append(getLocation());
		buf.append(' ');
		buf.append(getName());
		buf.append(']');

		return buf.toString();
	}

}	// End of Team
