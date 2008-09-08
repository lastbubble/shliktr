package org.lastbubble.shliktr.model;

/**
 * A type-safe enumeration representing the winner predicted by a player's
 * pick.
 *
 * @see	Pick
 *
 * @version $Id$
 */
public enum Winner
{
	HOME("home"),
	AWAY("away");

	private String value;

	Winner( String value ) { this.value = value; }

	public String getValue() { return this.value; }

	public String toString() { return getValue(); }

}	// End of Winner