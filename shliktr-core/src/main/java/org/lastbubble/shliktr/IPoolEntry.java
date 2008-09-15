package org.lastbubble.shliktr;

import java.util.List;

/**
 * @version $Id$
 */
public interface IPoolEntry
{
	IWeek getWeek();

	IPlayer getPlayer();

	List<? extends IPick> getPicks();

	int getTiebreaker();

	void setTiebreaker( int tiebreaker );
}