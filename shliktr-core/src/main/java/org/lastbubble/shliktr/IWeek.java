package org.lastbubble.shliktr;

import java.util.List;

/**
 * @version $Id$
 */
public interface IWeek
{
	int getWeekNumber();

	List<? extends IGame> getGames();

	String getTiebreaker();

	void setTiebreaker( String s );

	int getTiebreakerAnswer();

	void setTiebreakerAnswer( int n );
}