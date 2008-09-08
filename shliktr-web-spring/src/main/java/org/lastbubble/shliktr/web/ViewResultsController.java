package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.PlayerScore;
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
public class ViewResultsController extends AbstractController
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

		if( player != null && player.getUsername().equals("eric") )
		{
			model.put("isAdmin", Boolean.TRUE);
			model.put("players", this.poolService.findPlayersForWeek(week));
		}

		List<PlayerScore> scores = this.poolService.findScoresForWeek(week);
		model.put("scores", scores);

		List<PlayerScore> closest = new ArrayList<PlayerScore>();

		int min = Integer.MAX_VALUE;
		for( PlayerScore score : scores )
		{
			if( score.getTiebreakerDiff() < min )
			{
				closest.clear();
				closest.add(score);
				min = score.getTiebreakerDiff();
			}
			else if( score.getTiebreakerDiff() == min )
			{
				closest.add(score);
			}
		}
		model.put("closest", closest);

		return new ModelAndView("viewResults", model);
	}

}	// End of ViewResultsController
