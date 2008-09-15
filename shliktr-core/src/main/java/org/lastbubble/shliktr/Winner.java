package org.lastbubble.shliktr;

/**
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
}