package org.lastbubble.shliktr.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @version $Id$
 */
public class LoginController extends AbstractController
{
	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public LoginController()
	{
		setSupportedMethods( new String[] { METHOD_GET });
	}


	//-------------------------------------------------------------------------
	// AbstractController methods
	//-------------------------------------------------------------------------

	@Override
	public ModelAndView handleRequestInternal(
		HttpServletRequest request, HttpServletResponse response )
	{
		ModelAndView mv = new ModelAndView("login");

		return mv;
	}

}	// End of LoginController
