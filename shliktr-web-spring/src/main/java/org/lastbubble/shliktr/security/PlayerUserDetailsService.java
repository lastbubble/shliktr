package org.lastbubble.shliktr.security;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.service.PoolService;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;

/**
 * @version $Id$
 */
public class PlayerUserDetailsService implements UserDetailsService
{
	private PoolService poolService;


	//---------------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------------

	public void setPoolService( PoolService ps ) { this.poolService = ps; }


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	protected static String normalizeUsername( String username )
	{
		return username.toLowerCase().replace(' ', '_');
	}


	//---------------------------------------------------------------------------
	// Implements UserDetailsService
	//---------------------------------------------------------------------------

	/** @see	UserDetailsService#loadUserByUsername */
	public UserDetails loadUserByUsername( String username )
	throws UsernameNotFoundException
	{
		IPlayer player = this.poolService
			.findPlayerByUsername(normalizeUsername(username));

		if( player == null )
		{
			throw new UsernameNotFoundException("No player exists with that name!");
		}

		return new PlayerUserDetails(player);
	}

}