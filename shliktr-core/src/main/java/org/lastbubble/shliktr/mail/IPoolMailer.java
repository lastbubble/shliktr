package org.lastbubble.shliktr.mail;

import org.lastbubble.shliktr.IPoolEntry;

/**
 * @version $Id$
 */
public interface IPoolMailer
{
	void mailEntry( IPoolEntry entry ) throws Exception;
}