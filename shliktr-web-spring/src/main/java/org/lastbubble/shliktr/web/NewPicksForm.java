package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.impl.PickImpl;

import java.util.List;

/**
 * @version $Id$
 */
public class NewPicksForm
{
	private int weekId;
	private int playerId;
	private IPick[] picks;
	private int tiebreaker;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public NewPicksForm( IWeek week )
	{
		this.weekId = week.getWeekNumber();

		List<? extends IGame> games = week.getGames();

		int gameCnt = games.size();
		this.picks = new IPick[gameCnt];
		for( int i = 0; i < gameCnt; i++ )
		{
			this.picks[i] = new PickImpl(games.get(i));
		}
	}


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public int getWeekId() { return this.weekId; }

	public void setWeekId( int n ) { this.weekId = n; }

	public int getPlayerId() { return this.playerId; }

	public void setPlayerId( int n ) { this.playerId = n; }

	public IPick[] getPicks() { return this.picks; }

	public int getTiebreaker() { return this.tiebreaker; }

	public void setTiebreaker( int n ) { this.tiebreaker = n; }


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public String toString()
	{
		StringBuilder buf = new StringBuilder()
			.append(getClass().getName())
			.append("[")
			.append("week=")
			.append(getWeekId())
			.append(",")
			.append("player=")
			.append(getPlayerId())
			.append(",")
			.append("picks=")
			.append("[");

		for( int i = 0; i < getPicks().length; i++ )
		{
			if( i > 0 ) buf.append(",");
			buf.append(getPicks()[i]);
		}

		return buf.append("]")
			.append(",")
			.append(getTiebreaker())
			.append("]")
			.toString();
	}
}
