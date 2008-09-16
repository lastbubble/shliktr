package org.lastbubble.shliktr;

import java.util.Date;

/**
 * @version $Id$
 */
public interface IGame
{
	ITeam getHomeTeam();

	ITeam getAwayTeam();

	int getHomeScore();

	void setHomeScore( Integer n );

	int getAwayScore();

	void setAwayScore( Integer n );

	Date getPlayedOn();

	boolean isComplete();

	Winner getWinner();
}