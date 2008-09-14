package org.lastbubble.shliktr;

/**
 * @version $Id$
 */
public interface IPlayer
{
	String getUsername();

	String getName();

	String getPassword();

	String getEmailAddress();

	boolean isActive();
}