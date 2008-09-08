package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.model.Team;

import java.awt.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
 * A UI for viewing an NFL team.
 *
 * @version $Id$
 */
public final class TeamUI
{
	private static final Map ICON_BY_ABBR = new HashMap();


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	/* Constructor is private for utility class */
	private TeamUI() { }


	//-------------------------------------------------------------------------
	// Utility methods
	//-------------------------------------------------------------------------

	synchronized static Icon getTeamIcon( Team team )
	{
		String abbr = (team != null) ? team.getAbbr() : null;

		if( abbr == null ) return null;

		Icon icon = (Icon) ICON_BY_ABBR.get(abbr);

		if( icon == null && ICON_BY_ABBR.containsKey(abbr) == false )
		{
			String iconPath = new StringBuilder()
				.append(TeamUI.class.getPackage().getName().replace('.', '/'))
				.append('/')
				.append(abbr.toLowerCase().trim())
				.append(".gif")
				.toString();

			try {
				ImageIcon imgIcon = new ImageIcon(
					TeamUI.class.getClassLoader().getResource(iconPath));

				int width = imgIcon.getIconWidth();
				int height = imgIcon.getIconHeight();

				Image img = imgIcon.getImage().getScaledInstance(
					width / 2, height / 2, Image.SCALE_SMOOTH);
				icon = new ImageIcon(img);

			} catch( Throwable t ) { }

			ICON_BY_ABBR.put(abbr, icon);
		}

		return icon;
	}

}	// End of TeamUI
