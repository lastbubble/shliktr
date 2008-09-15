package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPlayer;

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
		ServletRequest request, IPlayer player )
	{
		request.setAttribute(PLAYER_REQUEST_ATTRIBUTE, player);
	}

	public static void removePlayerFromRequest( ServletRequest request )
	{
		request.removeAttribute(PLAYER_REQUEST_ATTRIBUTE);
	}

	public static IPlayer getPlayerFromRequest( ServletRequest request )
	{
		return (IPlayer) request.getAttribute(PLAYER_REQUEST_ATTRIBUTE);
	}
}
