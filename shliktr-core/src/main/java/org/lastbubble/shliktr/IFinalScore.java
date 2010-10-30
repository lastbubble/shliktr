package org.lastbubble.shliktr;

import java.util.List;

/**
 * @version $Id$
 */
public interface IFinalScore
{
	IPlayer getPlayer();

	int getPoints();

	int getTotal();

	List<PoolResult> getResults();
}