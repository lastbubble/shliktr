package org.lastbubble.shliktr;

/**
 * @version $Id$
 */
public interface IPlayer
{
	Integer getId();

	String getUsername();

	String getName();

	String getPassword();

	String getEmailAddress();

	boolean isActive();
}