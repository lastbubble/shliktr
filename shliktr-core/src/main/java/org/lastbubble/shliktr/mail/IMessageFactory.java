package org.lastbubble.shliktr.mail;

import org.lastbubble.shliktr.IPoolEntry;

/**
 * @version $Id$
 */
public interface IMessageFactory
{
	String messageForEntry( IPoolEntry entry ) throws Exception;
}