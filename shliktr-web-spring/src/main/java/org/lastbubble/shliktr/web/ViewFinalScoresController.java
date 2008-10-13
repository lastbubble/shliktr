package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.service.PoolService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @version $Id$
 */
public class ViewFinalScoresController extends AbstractController
{
	private static final int[] WEEK_IDS = new int[] {
		1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17
	};

	protected PoolService poolService;

	private String viewName;


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public void setViewName( String s ) { this.viewName = s; }

	public void setPoolService( PoolService ps ) { this.poolService = ps; }


	//-------------------------------------------------------------------------
	// AbstractController methods
	//-------------------------------------------------------------------------

	@Override
	protected final ModelAndView handleRequestInternal(
		HttpServletRequest request, HttpServletResponse response )
	throws Exception
	{
		ModelAndView modelAndView = new ModelAndView(this.viewName);

		modelAndView.addObject("weekIds", WEEK_IDS);
		modelAndView.addObject("bank", this.poolService.findBank());

		return modelAndView;
	}
}
