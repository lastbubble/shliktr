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
		StringBuffer buf = new StringBuffer();
		buf.append(getClass().getName());
		buf.append("[");
		buf.append("weekId=");
		buf.append(getWeekId());
		buf.append(",");
		buf.append("playerId=");
		buf.append(getPlayerId());
		buf.append("]");
		return buf.toString();
	}

}	// End of PicksCriteria
