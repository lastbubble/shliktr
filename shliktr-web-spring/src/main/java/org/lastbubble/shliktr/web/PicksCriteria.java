package org.lastbubble.shliktr.web;

/**
 * @version	$Id$
 */
public class PicksCriteria
{
	private int weekId;
	private int playerId;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public PicksCriteria()
	{
		setWeekId(1);
		setPlayerId(1);
	}


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public int getWeekId() { return this.weekId; }

	public void setWeekId( int n ) { this.weekId = n; }

	public int getPlayerId() { return this.playerId; }

	public void setPlayerId( int n ) { this.playerId = n; }


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public String toString()
	{
		return new StringBuilder()
			.append(getClass().getName())
			.append("[")
			.append("weekId=")
			.append(getWeekId())
			.append(",")
			.append("playerId=")
			.append(getPlayerId())
			.append("]")
			.toString();
	}
}
