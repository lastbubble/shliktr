package org.lastbubble.shliktr;

import org.lastbubble.shliktr.model.Winner;

/**
 * @version $Id$
 */
public interface IPick
{
	IGame getGame();

	Winner getWinner();

	void setWinner( Winner winner );

	ITeam getTeam();

	int getRanking();

	void setRanking( int ranking );
}