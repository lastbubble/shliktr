package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.Game;
import org.lastbubble.shliktr.model.Pick;
import org.lastbubble.shliktr.model.Week;

import java.util.List;

/**
 * @version $Id$
 */
public class NewPicksForm
{
	private int weekId;
	private int playerId;
	private Pick[] picks;
	private int tiebreaker;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public NewPicksForm( Week week )
	{
		this.weekId = week.getId().intValue();

		List<Game> games = week.getGames();

		int gameCnt = games.size();
		this.picks = new Pick[gameCnt];
		for( int i = 0; i < gameCnt; i++ )
		{
			this.picks[i] = new Pick(games.get(i));
		}
	}


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public int getWeekId() { return this.weekId; }

	public void setWeekId( int n ) { this.weekId = n; }

	public int getPlayerId() { return this.playerId; }

	public void setPlayerId( int n ) { this.playerId = n; }

	public Pick[] getPicks() { return this.picks; }

	public int getTiebreaker() { return this.tiebreaker; }

	public void setTiebreaker( int n ) { this.tiebreaker = n; }


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getName());
		buf.append("[");
		buf.append("week=");
		buf.append(getWeekId());
		buf.append(",");
		buf.append("player=");
		buf.append(getPlayerId());
		buf.append(",");
		buf.append("picks=");
		buf.append("[");
		for( int i = 0; i < getPicks().length; i++ )
		{
			if( i > 0 ) buf.append(",");
			buf.append(getPicks()[i]);
		}
		buf.append("]");
		buf.append(",");
		buf.append(getTiebreaker());
		buf.append("]");
		return buf.toString();
	}

}	// End of NewPicksForm
