package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/**
 * @version $Id$
 */
public class ViewPicksController extends WeekController
{
	//-------------------------------------------------------------------------
	// WeekController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleWeek( ModelAndView modelAndView,
		IWeek week, IPlayer player, HttpServletRequest request )
	{
		if( player != null )
		{
			if( player.getUsername().equals("eric") )
			{
				modelAndView.addObject("isAdmin", Boolean.TRUE);
			}
		}

		if( player == null || player.getUsername().equals("eric") )
		{
			int playerId = 0;

			String s = request.getParameter("playerId");
			if( s != null && s.length() > 0 )
			{
				try { playerId = Integer.parseInt(s); }
				catch( NumberFormatException e ) { }
			}

			if( playerId > 0 )
			{
				player = this.poolService.findPlayerById(playerId);
			}

			if( player == null )
			{
				throw new IllegalArgumentException("Invalid player id: "+s);
			}
		}

		IPoolEntry entry = this.poolService.findEntry(week, player, false);
		modelAndView.addObject("picks", entry);

		return modelAndView;
	}
}
