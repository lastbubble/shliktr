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
		return new ModelAndView(this.viewName)
			.addObject("bank", this.poolService.findBank());
	}
}
