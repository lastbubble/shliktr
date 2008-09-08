package org.lastbubble.shliktr.web;

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
public class ViewWeekController extends AbstractController
{
	private static final int[] WEEK_IDS = new int[] {
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17
	};

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

		model.put("weekIds", WEEK_IDS);

		Week week = null;

		String s = request.getParameter("weekId");
		if( s != null && s.length() > 0 )
		{
			int weekId = 1;

			try { weekId = Integer.parseInt(s); }
			catch( NumberFormatException e ) { }

			week = this.poolService.findWeekById(weekId);
		}
		else
		{
			week = this.poolService.findCurrentWeek();
		}

		model.put("week", week);

		Player player = WebUtils.getPlayerFromRequest(request);
		model.put("player", player);

		Set<Player> players = this.poolService.findPlayersForWeek(week);

		if( player != null )
		{
			if( player.getUsername().equals("eric") )
			{
				model.put("isAdmin", Boolean.TRUE);
				model.put("players", players);
			}
			else
			{
				model.put("hasPicks", players.contains(player));
			}
		}

		return new ModelAndView("viewWeek", model);
	}

}	// End of ViewWeekController
