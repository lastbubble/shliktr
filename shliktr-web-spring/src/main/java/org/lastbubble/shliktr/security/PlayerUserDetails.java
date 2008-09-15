package org.lastbubble.shliktr.security;

import org.lastbubble.shliktr.IPlayer;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.GrantedAuthorityImpl;
import org.acegisecurity.userdetails.UserDetails;

/**
 * @version $Id$
 */
public class PlayerUserDetails implements UserDetails
{
	private static GrantedAuthority[] DEFAULT_AUTHORITIES =
		new GrantedAuthority[] {
			new GrantedAuthorityImpl("ROLE_PLAYER"),
		};

	private IPlayer player;
	private GrantedAuthority[] authorities;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	PlayerUserDetails( IPlayer player )
	{
		this.player = player;
		this.authorities = DEFAULT_AUTHORITIES;

		if( player.getUsername().equals("eric") )
		{
			int len = this.authorities.length;
			GrantedAuthority[] auths = new GrantedAuthority[ len + 1 ];
			System.arraycopy(this.authorities, 0, auths, 0, len);
			auths[ len ] = new GrantedAuthorityImpl("ROLE_ADMIN");
			this.authorities = auths;
		}
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public IPlayer getPlayer() { return this.player; }


	//-------------------------------------------------------------------------
	// Implements UserDetails
	//-------------------------------------------------------------------------

	public GrantedAuthority[] getAuthorities() { return this.authorities; }

	public String getPassword() { return getPlayer().getPassword(); }

	public String getUsername() { return getPlayer().getUsername(); }

	public boolean isAccountNonExpired() { return true; }

	public boolean isAccountNonLocked() { return true; }

	public boolean isCredentialsNonExpired() { return true; }

	public boolean isEnabled() { return true; }
}
