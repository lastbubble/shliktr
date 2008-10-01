package org.lastbubble.shliktr.tools;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.mail.IPoolMailer;
import org.lastbubble.shliktr.service.PoolService;

/**
 * @version $Id$
 */
public class PoolMailer
{
	private PoolService poolService;
	private IPoolMailer poolMailer;


	//---------------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------------

	public void setPoolService( PoolService poolService )
	{
		this.poolService = poolService;
	}

	public void setPoolMailer( IPoolMailer poolMailer )
	{
		this.poolMailer = poolMailer;
	}


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	public void mail( String username, int weekNumber ) throws Exception
	{
		IPlayer player = this.poolService.findPlayerByUsername(username);
		IWeek week = this.poolService.findWeekById(weekNumber);

		IPoolEntry entry = this.poolService.findEntry(week, player, false);

		this.poolMailer.mailEntry(entry);
	}
}