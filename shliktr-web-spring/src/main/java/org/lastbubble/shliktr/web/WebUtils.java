package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.Player;

import javax.servlet.ServletRequest;

/**
 * @version $Id$
 */
public final class WebUtils
{
	public static final String PLAYER_REQUEST_ATTRIBUTE = "PLAYER_ATTRIBUTE";


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	private WebUtils() { }



	//-------------------------------------------------------------------------
	// Utility methods
	//-------------------------------------------------------------------------

	public static void addPlayerToRequest(
		ServletRequest request, Player player )
	{
		request.setAttribute(PLAYER_REQUEST_ATTRIBUTE, player);
	}

	public static void removePlayerFromRequest( ServletRequest request )
	{
		request.removeAttribute(PLAYER_REQUEST_ATTRIBUTE);
	}

	public static Player getPlayerFromRequest( ServletRequest request )
	{
		return (Player) request.getAttribute(PLAYER_REQUEST_ATTRIBUTE);
	}

}	// End of WebUtils
