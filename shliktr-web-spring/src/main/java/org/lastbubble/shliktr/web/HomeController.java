package org.lastbubble.shliktr.web;

/**
 * @version $Id$
 */
public class HomeController extends WeekController
{
	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public HomeController()
	{
		setSupportedMethods( new String[] { METHOD_GET });
	}
}
