package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.ITeam;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PlayerPrediction;
import org.lastbubble.shliktr.PoolResult;
import org.lastbubble.shliktr.StringUtils;
import org.lastbubble.shliktr.Winner;
import org.lastbubble.shliktr.service.PoolService;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A <code>View</code> that displays the current week's game results and
 * the week's tiebreaker result.
 *
 * @version $Id$
 */
public class GameResultsView extends JPanel
implements View, ActionListener, ChangeListener, DocumentListener
{
	private PoolService poolService;

	// model

	private IWeek week;
	/** whether the view has changed the data */
	private boolean dataChanged;

	// view

	private JPanel gamesPanel;
	private GameUI[] gameUIs;
	private JTextArea tiebreakerFld;
	private JTextField tiebreakerAnswerFld;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public GameResultsView( PoolService ps )
	{
		super( new BorderLayout());

		this.poolService = ps;

		initComponents();
	}

	private void initComponents()
	{
		JPanel p;
		JButton btn;

		p = new JPanel( new FlowLayout(FlowLayout.TRAILING));
		btn = new JButton("Show Results");
		btn.setActionCommand("show_results");
		btn.addActionListener(this);
		p.add(btn);
		btn = new JButton("Predict Results");
		btn.setActionCommand("predict_results");
		btn.addActionListener(this);
		p.add(btn);
		btn = new JButton("Show Form");
		btn.setActionCommand("show_form");
		btn.addActionListener(this);
		p.add(btn);
		add(p, BorderLayout.NORTH);

		this.gameUIs = new GameUI[16];
		for( int i = 0; i < this.gameUIs.length; i++ )
		{
			this.gameUIs[i] = new GameUI();
			this.gameUIs[i].addChangeListener(this);
		}

		this.gamesPanel = new JPanel( new GridLayout(17, 2));
		add(this.gamesPanel, BorderLayout.CENTER);

		this.tiebreakerFld = new JTextArea();
		this.tiebreakerFld.setRows(5);
		this.tiebreakerFld.setLineWrap(true);
		this.tiebreakerFld.setWrapStyleWord(true);
		this.tiebreakerAnswerFld = new JTextField(5);

		JPanel bottom = new JPanel( new BorderLayout());

		bottom.add( new JScrollPane(this.tiebreakerFld), BorderLayout.CENTER);
		bottom.add(this.tiebreakerAnswerFld, BorderLayout.LINE_END);

		add(bottom, BorderLayout.SOUTH);
	}


	//-------------------------------------------------------------------------
	// Model
	//-------------------------------------------------------------------------

	public void setWeek( IWeek week )
	{
		this.week = week;

		updateView();
	}

	public boolean hasChanged() { return this.dataChanged; }


	//-------------------------------------------------------------------------
	// View
	//-------------------------------------------------------------------------

	private void updateView()
	{
		this.gamesPanel.removeAll();

		List<? extends IGame> games = this.week.getGames();

		int gameCnt = games.size();

		for( int i = 0; i < this.gameUIs.length; i++ )
		{
			this.gameUIs[i].setGame((i < gameCnt) ? games.get(i) : null);
		}

		this.gamesPanel.add( new JLabel("AWAY"));
		this.gamesPanel.add( new JLabel("HOME"));

		for( int i = 0; i < this.gameUIs.length; i++ )
		{
			GameUI ui = this.gameUIs[i];
			if( ui.getGame() == null ) break;

			this.gamesPanel.add(ui.getAwayChooser());
			this.gamesPanel.add(ui.getHomeChooser());
		}

		this.tiebreakerFld.getDocument().removeDocumentListener(this);
		this.tiebreakerFld.setText(this.week.getTiebreaker());
		this.tiebreakerFld.getDocument().addDocumentListener(this);

		this.tiebreakerAnswerFld.getDocument().removeDocumentListener(this);
		this.tiebreakerAnswerFld.setText(String.valueOf(
			this.week.getTiebreakerAnswer()));
		this.tiebreakerAnswerFld.getDocument().addDocumentListener(this);

		revalidate();
		repaint();

		this.dataChanged = false;
	}


	//-------------------------------------------------------------------------
	// Implements View
	//-------------------------------------------------------------------------

	public boolean canChoosePlayer() { return false; }

	public Component render() { return this; }

	public void setModel( IWeek week, IPoolEntry entry )
	{
		setWeek(week);
	}

	public void save()
	{
		this.week.setTiebreaker(this.tiebreakerFld.getText());

		String s = this.tiebreakerAnswerFld.getText();
		try { this.week.setTiebreakerAnswer(Integer.parseInt(s)); }
		catch( NumberFormatException e ) {}

		this.poolService.saveWeek(this.week);

		this.dataChanged = false;
	}

	public boolean exit()
	{
		if( hasChanged() )
		{
			int result = JOptionPane.showConfirmDialog(this, "Save changes?");

			if( result == JOptionPane.CANCEL_OPTION )
				return false;

			if( result == JOptionPane.YES_OPTION )
				save();
		}

		return true;
	}


	//-------------------------------------------------------------------------
	// Actions
	//-------------------------------------------------------------------------

	public void actionPerformed( ActionEvent e )
	{
		String command = e.getActionCommand();

		if( "show_results".equals(command) )
		{
			showResults();
		}
		else if( "predict_results".equals(command) )
		{
			predictResults();
		}
		else if( "show_form".equals(command) )
		{
			showForm();
		}
	}

	private void showResults()
	{
		List games = new ArrayList();
		for( int i = 0; i < this.gameUIs.length; i++ )
		{
			if( this.gameUIs[i].getGame() != null )
				games.add(this.gameUIs[i].getGame());
		}

		List<PoolResult> results = this.poolService
			.findResultsForWeek(this.week.getWeekNumber());

		StringBuffer buf = new StringBuffer();

		buf.append(StringUtils.pad("PLAYER", 12, false));
		buf.append(' ');
		buf.append(StringUtils.pad("SCORE", 5, true));
		buf.append(' ');
		buf.append(StringUtils.pad("W-L", 4, true));
		buf.append(' ');
		buf.append(StringUtils.pad("LOST", 4, true));
		buf.append(' ');
		buf.append(StringUtils.pad("REM", 4, true));
		buf.append(' ');
		buf.append(StringUtils.pad("TIE", 3, true));
		buf.append('\n');

		for( PoolResult result : results )
		{
			buf.append(
				StringUtils.pad(result.getPlayer().getName(), 12, false));
			buf.append(' ');
			buf.append(StringUtils.pad(result.getPoints(), 5, true));
			buf.append(' ');

			String record = new StringBuilder()
				.append(result.getGamesWon())
				.append('-')
				.append(result.getGamesLost())
				.toString();

			buf.append(StringUtils.pad(record, 4, true));
			buf.append(' ');
			buf.append(StringUtils.pad(result.getPointsLost(), 4, true));
			buf.append(' ');
			buf.append(StringUtils.pad(result.getPointsRemaining(), 4, true));
			buf.append(' ');
			buf.append(StringUtils.pad(result.getTiebreakerDiff(), 3, true));
			buf.append('\n');
		}

		float total = results.size() * 5.00F;
		buf.append('\n');
		buf.append("First place: $");
		buf.append(total * 0.7F);
		buf.append('\n');
		buf.append("Second place: $");
		buf.append(total * 0.2F);
		buf.append('\n');
		buf.append("Year-end pot: $");
		buf.append(total * 0.1F);
		buf.append('\n');

		JTextArea text = new JTextArea();
		text.setFont( new Font("monospaced", Font.PLAIN, 12));
		text.setText(buf.toString());
		text.setCaretPosition(0);

		JScrollPane scroll = new JScrollPane(text);

		Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
		JDialog dlg = new JDialog(parent,
			"Results for week "+this.week.getWeekNumber(), true);
		dlg.getContentPane().add(scroll);
		dlg.pack();
		dlg.show();
	}

	private void predictResults()
	{
		List<Winner> winners = new ArrayList<Winner>(this.week.getGames().size());

		int unfinishedCnt = 0;

		for( int i = 0; i < this.gameUIs.length; i++ )
		{
			IGame game = this.gameUIs[i].getGame();
			if( game == null ) continue;

			Winner winner = game.getWinner();
			winners.add(winner);

			if( winner == null )
				unfinishedCnt++;
		}

		System.out.println(">> predicting results for final "+unfinishedCnt+
			" games");

		Map<? extends IPlayer, PlayerPrediction> m = this.poolService
			.predictResults(this.week, winners);

		List<PlayerPrediction> predictions = new ArrayList<PlayerPrediction>();
		predictions.addAll(m.values());
		Collections.sort(predictions);

		StringBuilder buf = new StringBuilder();

		buf.append("Predictions:\n");
		buf.append("============\n");
		buf.append(StringUtils.pad("PLAYER", 12, false));
		buf.append(' ');
		buf.append(StringUtils.pad("WINS", 5, true));
		buf.append('\n');

		for( PlayerPrediction pp : predictions )
		{
			buf.append(StringUtils.pad(pp.getPlayer().getName(), 12, false));
			buf.append(' ');
			buf.append(StringUtils.pad(pp.getWinningOutcomes().size(), 5, true));

			Winner[] mustWins = pp.getMustWins();
			if( mustWins != null )
			{
				int listed = 0;

				for( int j = 0; j < mustWins.length; j++ )
				{
					Winner winner = mustWins[j];
					if( winner != null )
					{
						if( listed == 0 )
						{
							buf.append("  MUST: ");
						}

						if( listed > 0 ) buf.append(',');
/*
						IGame game = unfinishedGames[j];
						switch( winner )
						{
							case HOME:
								buf.append(game.getHomeTeam().getAbbr().toUpperCase());
								break;
							case AWAY:
								buf.append(game.getAwayTeam().getAbbr().toUpperCase());
								break;
						}
*/
						listed++;
					}
				}
			}

			buf.append('\n');

			List<String> winningOutcomes = pp.getWinningOutcomes();

			if( winningOutcomes.size() <= 16 )
			{
				for( String outcome : winningOutcomes )
				{
					buf.append("                 ");
					for( int k = 0; k < unfinishedCnt; k++ )
					{
/*
						if( k > 0 ) buf.append(',');
						IGame game = unfinishedGames[k];
						ITeam team = (outcome.charAt(k) == '1') ?
							game.getHomeTeam() : game.getAwayTeam();
						buf.append(team.getAbbr().toUpperCase());
*/
					}
					if( outcome.endsWith("*") )
						buf.append(" (tie)");
					buf.append('\n');
				}
			}
		}

		JTextArea text = new JTextArea();
		text.setFont( new Font("monospaced", Font.PLAIN, 12));
		text.setText(buf.toString());
		text.setCaretPosition(0);

		JScrollPane scroll = new JScrollPane(text);

		Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
		JDialog dlg = new JDialog(parent,
			"Predictions for week "+this.week.getWeekNumber(), true);
		dlg.getContentPane().add(scroll);
		dlg.pack();
		dlg.show();
	}

	private void showForm()
	{
		List<IGame> games = new ArrayList<IGame>();
		for( int i = 0; i < this.gameUIs.length; i++ )
		{
			if( this.gameUIs[i].getGame() != null )
				games.add(this.gameUIs[i].getGame());
		}

		StringBuffer buf = new StringBuffer();

		int weekNumber = this.week.getWeekNumber();

		buf.append("WEEK ");
		buf.append(weekNumber);
		buf.append(" PICKS:\n");

		if( weekNumber > 9 ) buf.append('=');
		buf.append("=============\n");

		buf.append("Here's the form for Week ");
		buf.append(weekNumber);
		buf.append(" (note that there are ");
		buf.append(games.size());
		buf.append(" games this week):\n\n");

		buf.append("AWAY           HOME           PICK           RANK (1-");
		buf.append(games.size());
		buf.append(")\n");

		buf.append("=============  =============  =============  ===========");
		buf.append("\n");

		DateFormat dayOfWeekFmt = new SimpleDateFormat("EEEE");

		String dayOfWeek = null;

		for( IGame game : games )
		{
			if( game.getPlayedOn() != null )
			{
				String s = dayOfWeekFmt.format(game.getPlayedOn());

				if(! s.equals(dayOfWeek) )
				{
					dayOfWeek = s;
					buf.append(dayOfWeek);
					buf.append("'s game(s):\n");
				}
			}

			buf.append(
				StringUtils.pad(game.getAwayTeam().getLocation(), 13, false));
			buf.append("  ");
			buf.append(
				StringUtils.pad(game.getHomeTeam().getLocation(), 13, false));
			buf.append("  ");
			buf.append("_____________  ___________");
			buf.append('\n');
		}

		buf.append('\n');
		buf.append('\n');
		buf.append("Tiebreaker question:\n");
		buf.append("====================\n");

		String tiebreaker = this.week.getTiebreaker();
		buf.append((tiebreaker != null) ? tiebreaker : "");
		buf.append(" _____\n");
		buf.append('\n');
		buf.append("Week ");
		buf.append(weekNumber);
		buf.append(" picks are due by midnight (Pacific time) ");
		Date weekStart = null;//this.week.getStart();
		if( weekStart != null )
		{
			Date before = new Date(weekStart.getTime() - 12 * 60 * 60);
			buf.append( new SimpleDateFormat("EEEE, MMM. d").format(before));
		}
		buf.append('.');

		JTextArea text = new JTextArea();
		text.setFont( new Font("monospaced", Font.PLAIN, 12));
		text.setText(buf.toString());
		text.setCaretPosition(0);

		JScrollPane scroll = new JScrollPane(text);

		Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
		JDialog dlg = new JDialog(parent,
			"Week "+this.week.getWeekNumber()+" form", true);
		dlg.getContentPane().add(scroll);
		dlg.pack();
		dlg.show();
	}


	//-------------------------------------------------------------------------
	// Implements ChangeListener
	//-------------------------------------------------------------------------

	public void stateChanged( ChangeEvent e ) { this.dataChanged = true; }


	//---------------------------------------------------------------------
	// Implements DocumentListener
	//---------------------------------------------------------------------

	public void insertUpdate( DocumentEvent e ) { documentUpdated(); }
	public void removeUpdate( DocumentEvent e ) { documentUpdated(); }
	public void changedUpdate( DocumentEvent e ) { documentUpdated(); }

	private void documentUpdated()
	{
		this.dataChanged = true;
	}
}