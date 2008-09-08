package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.service.PoolService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @version $Id$
 */
public class HomeController extends AbstractController
{
	private PoolService poolService;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public HomeController()
	{
		setSupportedMethods( new String[] { METHOD_GET });
	}


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public void setPoolService( PoolService ps ) { this.poolService = ps; }


	//-------------------------------------------------------------------------
	// AbstractController methods
	//-------------------------------------------------------------------------

	@Override
	public ModelAndView handleRequestInternal(
		HttpServletRequest request, HttpServletResponse response )
	{
		ModelAndView mv = new ModelAndView("home");

		mv.addObject("week", this.poolService.findCurrentWeek());
		mv.addObject("player", WebUtils.getPlayerFromRequest(request));

		return mv;
	}

}	// End of HomeController
