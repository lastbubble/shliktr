package org.lastbubble.shliktr.mail;

import org.lastbubble.shliktr.IPoolEntry;

/**
 * @version $Id$
 */
public interface IPoolMailer
{
	boolean isEnabled();

	void mailEntry( IPoolEntry entry ) throws Exception;
}