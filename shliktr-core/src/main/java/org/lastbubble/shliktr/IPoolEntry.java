package org.lastbubble.shliktr;

import java.util.List;

/**
 * @version $Id$
 */
public interface IPoolEntry
{
	List<? extends IPick> getPicks();

	void setTiebreaker( int tiebreaker );
}