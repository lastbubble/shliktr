package org.lastbubble.shliktr;

import java.util.Comparator;

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

	public static final Comparator<IPick> COMPARE_RANKING =
		new Comparator<IPick>() {
			public int compare( IPick pick1, IPick pick2 )
			{
				return pick1.getRanking() - pick2.getRanking();
			}
		};
}