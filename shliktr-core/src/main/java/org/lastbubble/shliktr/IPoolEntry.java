package org.lastbubble.shliktr;

import java.util.Comparator;
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

	void computeScore();

	int getScore();

	int getGamesWon();

	int getGamesLost();

	int getLost();

	int getRemaining();

	int getTiebreakerDiff();

	boolean validate( String[] errmsg );

	public static final Comparator<IPoolEntry> COMPARE_SCORE =
		new Comparator<IPoolEntry>() {
			public int compare( IPoolEntry entry1, IPoolEntry entry2 )
			{
				int scoreDelta = entry2.getScore() - entry1.getScore();
				if( scoreDelta != 0 )
					return scoreDelta;
				return entry1.getTiebreakerDiff() - entry2.getTiebreakerDiff();
			}
		};
}