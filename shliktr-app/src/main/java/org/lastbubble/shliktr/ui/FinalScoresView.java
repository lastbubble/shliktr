package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PlayerScore;
import org.lastbubble.shliktr.StringUtils;
import org.lastbubble.shliktr.service.PoolService;

import java.awt.Component;
import java.awt.Font;
import java.util.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * A <code>View</code> that displays the final scores, using the currently
 * selected week as the last week that was played.
 *
 * @version $Id$
 */
public class FinalScoresView implements View
{
	private PoolService poolService;
	private JScrollPane scroll;
	private JTextArea text;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public FinalScoresView( PoolService ps )
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

	public void setModel( IWeek week, IPoolEntry entry )
	{
		this.text.setText("");

		FinalScore finalScore;

		List<? extends IPlayer> players = this.poolService.findAllPlayers();
		List scores = new ArrayList();
		for( IPlayer player : players )
		{
			finalScore = new FinalScore(player);
			scores.add(finalScore);
		}

		PlayerScore result;
		for( int i = 1, last = week.getWeekNumber(); i <= last; i++ )
		{
			IWeek wk = this.poolService.findWeekById( new Integer(i));

			for( Iterator j = scores.iterator(); j.hasNext(); )
			{
				finalScore = (FinalScore) j.next();
				result = new PlayerScore(this.poolService
					.findEntry(wk, finalScore.getPlayer(), true)
				);

				finalScore.addScore(i, result.getScore());
			}
		}

		Collections.sort(scores);
		Collections.reverse(scores);

		StringBuffer buf = new StringBuffer();

		buf.append(StringUtils.pad("PLAYER", 10, false));
		buf.append(' ');
		buf.append(StringUtils.pad("SCORE", 5, true));
		for( int i = 1, last = week.getWeekNumber(); i <= last; i++ )
		{
			buf.append(' ');
			buf.append(StringUtils.pad("#(WEEK)", 7, true));
		}
		buf.append('\n');

		for( Iterator i = scores.iterator(); i.hasNext(); )
		{
			finalScore = (FinalScore) i.next();
			buf.append(
				StringUtils.pad(finalScore.getPlayer().getName(), 10, false));
			buf.append(' ');
			buf.append(
				StringUtils.pad(finalScore.getScore(), 5, true));
			for( int j = 0, last = week.getWeekNumber(); j < last; j++ )
			{
				buf.append(' ');
				buf.append(StringUtils.pad(
					String.valueOf(finalScore.getScore(j)), 3, true));
				buf.append('(');
				buf.append(StringUtils.pad(
					String.valueOf(finalScore.getWeek(j)), 2, true));
				buf.append(')');
			}
			buf.append('\n');
		}

		this.text.setText(buf.toString());
		this.text.setCaretPosition(0);
	}

	public void save() { /* do nothing */ }

	public boolean exit() { return true; }


	//-------------------------------------------------------------------------
	// Member class FinalScore
	//-------------------------------------------------------------------------

	private class FinalScore implements Comparable
	{
		private IPlayer im_player;
		private List im_weeklyScores;
		private int im_finalScore;

		private FinalScore( IPlayer player )
		{
			im_player = player;
			im_weeklyScores = new ArrayList();
		}

		private IPlayer getPlayer() { return im_player; }

		private void addScore( int week, int score )
		{
			im_weeklyScores.add( new WeeklyScore(week, score));
		}

		private int getScore()
		{
			if( im_finalScore == 0 )
			{
				Collections.sort(im_weeklyScores);
				Collections.reverse(im_weeklyScores);

				int cnt = im_weeklyScores.size();
				int last = (cnt <= 3) ? cnt : cnt - 3;
				WeeklyScore weeklyScore;
				for( int i = 0; i < last; i++ )
				{
					weeklyScore = (WeeklyScore) im_weeklyScores.get(i);
					im_finalScore += weeklyScore.getScore();
				}
			}

			return im_finalScore;
		}

		private int getScore( int i )
		{
			return ((WeeklyScore) im_weeklyScores.get(i)).getScore();
		}

		private int getWeek( int i )
		{
			return ((WeeklyScore) im_weeklyScores.get(i)).getWeek();
		}

		public int compareTo( Object obj )
		{
			FinalScore fs = (FinalScore) obj;
			return (getScore() - fs.getScore());
		}

		private class WeeklyScore implements Comparable
		{
			private int im_week;
			private int im_score;

			private WeeklyScore( int week, int score )
			{
				im_week = week;
				im_score = score;
			}

			private int getWeek() { return im_week; }

			private int getScore() { return im_score; }

			public int compareTo( Object obj )
			{
				WeeklyScore ws = (WeeklyScore) obj;
				return (getScore() - ws.getScore());
			}

		}	// End of WeeklyScore

	}	// End of FinalScore

}	// End of FinalScoresView
