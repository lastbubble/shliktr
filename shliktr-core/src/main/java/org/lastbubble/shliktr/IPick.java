package org.lastbubble.shliktr;

/**
 * @version $Id$
 */
public interface IPick
{
	IGame getGame();

	Winner getWinner();

	void setWinner( Winner winner );

	ITeam getTeam();

	boolean isCorrect();

	int getRanking();

	void setRanking( int ranking );
}