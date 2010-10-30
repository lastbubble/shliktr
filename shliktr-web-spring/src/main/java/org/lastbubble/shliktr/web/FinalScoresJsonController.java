package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IFinalScore;
import org.lastbubble.shliktr.IPoolBank;
import org.lastbubble.shliktr.PoolResult;
import org.lastbubble.shliktr.service.PoolService;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @version $Id$
 */
public class FinalScoresJsonController extends AbstractController
{
	protected PoolService poolService;


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public void setPoolService( PoolService ps ) { this.poolService = ps; }


	//-------------------------------------------------------------------------
	// AbstractController methods
	//-------------------------------------------------------------------------

	@Override
	protected final ModelAndView handleRequestInternal(
		HttpServletRequest request, HttpServletResponse response )
	throws Exception
	{
		JSONArray items = new JSONArray();

		IPoolBank bank = this.poolService.findBank();
		for( IFinalScore finalScore : bank.getFinalScores() )
		{
			JSONObject item = new JSONObject()
				.element("player", finalScore.getPlayer().getName())
				.element("score", finalScore.getPoints())
				.element("total", finalScore.getTotal());

			List<PoolResult> results = finalScore.getResults();
			for( int i = 0, cnt = results.size(); i < cnt; i++ )
			{
				PoolResult result = results.get(i);
				item.element("week"+i, new StringBuilder()
					.append(result.getPoints())
					.append(' ')
					.append('(').append(result.getWeek()).append(')')
					.toString()
				);
			}

			items.add(item);
		}

		return new ModelAndView( new JsonView()).addObject("items", items);
	}
}
