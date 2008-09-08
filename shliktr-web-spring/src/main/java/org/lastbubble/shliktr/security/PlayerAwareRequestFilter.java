package org.lastbubble.shliktr.security;

import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.web.WebUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;

/**
 * @version $Id$
 */
public class PlayerAwareRequestFilter implements Filter
{
	//-------------------------------------------------------------------------
	// Implements Filter
	//-------------------------------------------------------------------------

	public void init( FilterConfig filterConfig ) throws ServletException { }

	public void destroy() { }

	public void doFilter(
		ServletRequest request,
		ServletResponse response,
		FilterChain filterChain )
	throws IOException, ServletException
	{
		Authentication auth =
			SecurityContextHolder.getContext().getAuthentication();

		Player player = null;

		if( auth != null && auth.getPrincipal() instanceof PlayerUserDetails )
		{
			player = ((PlayerUserDetails) auth.getPrincipal()).getPlayer();
		}

		if( player != null )
		{
			WebUtils.addPlayerToRequest(request, player);
		}
		else
		{
			WebUtils.removePlayerFromRequest(request);
		}

		filterChain.doFilter(request, response);
	}

}	// PlayerAwareRequestFilter
