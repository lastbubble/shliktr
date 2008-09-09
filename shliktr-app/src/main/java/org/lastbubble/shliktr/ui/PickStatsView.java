package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.model.Picks;
import org.lastbubble.shliktr.model.StringUtils;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.service.PoolService;

import java.awt.Component;
import java.awt.Font;
import java.util.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A <code>View</code> that displays the pick statistics for individual
 * teams during the current week.
 *
 * @version $Id$
 */
public class PickStatsView implements View
{
	private PoolService poolService;
	private JScrollPane scroll;
	private JTextArea text;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public PickStatsView( PoolService ps )
	{
		this.poolService = ps;
		this.text = new JTextArea();
		this.text.setFont( new Font("monospaced", Font.PLAIN, 12));
		this.scroll = new JScrollPane(this.text);
	}


	//-------------------------------------------------------------------------
	// Implements View
	//-------------------------------------------------------------------------

	public boolean canChoosePlayer() { return false; }

	public Component render() { return this.scroll; }

	public void setModel( Week week, Picks picks )
	{
		this.text.setText("");

		List<TeamStats> stats = new ArrayList<TeamStats>();

		Map<String, List<Integer>> m = Collections.EMPTY_MAP;//this.poolService.getPickStats(week);
		for( String team : m.keySet() )
		{
			stats.add( new TeamStats(team, m.get(team)));
		}

		Collections.sort(stats);

		StringBuffer buf = new StringBuffer();

		buf.append(StringUtils.pad("TEAM", 18, false));
		buf.append(' ');
		buf.append(StringUtils.pad("#", 3, true));
		buf.append(' ');
		buf.append(StringUtils.pad("TOT", 3, true));
		buf.append(' ');
		buf.append("RANKINGS");
		buf.append('\n');

		for( TeamStats stat : stats )
		{
			buf.append(StringUtils.pad(stat.getTeam(), 18, false));
			buf.append(' ');
			buf.append(StringUtils.pad(stat.size(), 3, true));
			buf.append(' ');
			buf.append(StringUtils.pad(stat.getTotal(), 3, true));
			buf.append(' ');
			for( int j = 0, cnt = stat.size(); j < cnt; j++ )
			{
				if( j > 0 ) buf.append(',');
				buf.append(stat.getRanking(j));
			}
			buf.append('\n');
		}

		this.text.setText(buf.toString());
		this.text.setCaretPosition(0);
	}

	public void save() { /* do nothing */ }

	public boolean exit() { return true; }


	//-------------------------------------------------------------------------
	// Member class TeamStats
	//-------------------------------------------------------------------------

	private class TeamStats implements Comparable
	{
		private String im_sTeam;
		private List<Integer> im_rankings;

		private TeamStats( String team, List<Integer> rankings )
		{
			im_sTeam = team;
			im_rankings = rankings;
		}

		public String getTeam() { return im_sTeam; }

		public int size() { return im_rankings.size(); }

		public int getRanking( int i )
		{
			return im_rankings.get(i).intValue();
		}

		public int getTotal()
		{
			int total = 0;
			for( int i = 0, cnt = size(); i < cnt; i++ )
			{
				total += getRanking(i);
			}
			return total;
		}

		public int compareTo( Object obj )
		{
			if(! (obj instanceof TeamStats) ) return -1;

			TeamStats stats = (TeamStats) obj;
			int delta = stats.size() - size();
			if( delta == 0 )
			{
				delta = stats.getTotal() - getTotal();
			}
			return delta;
		}

	}	// End of TeamStats

}	// End of PickStatsView
