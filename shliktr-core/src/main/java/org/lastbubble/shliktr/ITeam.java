package org.lastbubble.shliktr;

import java.util.Comparator;

/**
 * @version $Id$
 */
public interface ITeam
{
	String getAbbr();

	String getLocation();

	String getName();

	public static final Comparator<ITeam> COMPARE_ABBR =
		new Comparator<ITeam>() {
			public int compare( ITeam team1, ITeam team2 )
			{
				String abbr1 = (team1 != null) ? team1.getAbbr() : null;
				String abbr2 = (team2 != null) ? team2.getAbbr() : null;

				if( abbr1 == null ) return (abbr2 == null) ? 0 : 1;

				return (abbr2 != null) ? abbr1.compareTo(abbr2) : -1;
			}
		};
}