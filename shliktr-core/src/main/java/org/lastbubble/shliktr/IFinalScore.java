package org.lastbubble.shliktr;

import java.util.List;

/**
 * @version $Id$
 */
public interface IFinalScore
{
	IPlayer getPlayer();

	int getPoints();

	List<PoolResult> getResults();
}