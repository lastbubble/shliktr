package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.Pick;
import org.lastbubble.shliktr.model.Picks;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.service.PoolService;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @version $Id$
 */
public class ViewPicksController extends AbstractController
{
	private PoolService poolService;


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public void setPoolService( PoolService ps ) { this.poolService = ps; }


	//-------------------------------------------------------------------------
	// AbstractController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleRequestInternal(
		HttpServletRequest request, HttpServletResponse response )
	throws Exception
	{
		Map model = new HashMap();

		int weekId = 1;

		String s = request.getParameter("weekId");
		if( s != null && s.length() > 0 )
		{
			try { weekId = Integer.parseInt(s); }
			catch( NumberFormatException e ) { }
		}

		Week week = this.poolService.findWeekById(weekId);
		model.put("week", week);

		Player player = WebUtils.getPlayerFromRequest(request);
		model.put("player", player);

		if( player != null )
		{
			if( player.getUsername().equals("eric") )
			{
				model.put("isAdmin", Boolean.TRUE);
			}
		}

		if( player == null || player.getUsername().equals("eric") )
		{
			int playerId = 0;

			s = request.getParameter("playerId");
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

		Picks picks = this.poolService.findPicksForPlayer(week, player, false);
		model.put("picks", picks);

		return new ModelAndView("viewPicks", model);
	}

}	// End of ViewPicksController
