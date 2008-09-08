package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.model.Picks;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.PlayerScore;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.service.PoolService;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 * NFL pool.
 *
 * @version $Id$
 */
public class Pool extends JFrame
{
	private PoolService poolService;

	private JComboBox m_weekChooser;
	private JComboBox picksChooser;

	private JPanel m_mainPanel;

	// views
	private View currentView;

	private View gameResultsView;
	private PlayerPicksView playerPicksView;
	private View pickStatsView;
	private View finalScoresView;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public Pool( PoolService ps )
	{
		// main frame setup
		super("Pool");

		this.poolService = ps;

		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e )
			{
				exit();
			}
		});

		// views
		this.gameResultsView = new GameResultsView(this.poolService);
		this.playerPicksView = new PlayerPicksView(this.poolService);
		this.pickStatsView = new PickStatsView(this.poolService);
		this.finalScoresView = new FinalScoresView(this.poolService);

		// menu actions
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu;

		menu = new JMenu("Season");
		menu.add( new SaveAction());
		menu.add( new JSeparator());
		menu.add( new Exit());
		menuBar.add(menu);

		menu = new JMenu("Player");
		menu.add( new ChangeView("Picks", this.playerPicksView));
		menu.add( new ValidatePicks());
		menuBar.add(menu);

		menu = new JMenu("Week");
		menu.add( new ChangeView("Game Results", this.gameResultsView));
		menu.add( new AddPlayer());
		menu.add( new ChangeView("Pick Statistics", this.pickStatsView));
		menu.add( new ChangeView("Final Scores", this.finalScoresView));

		menuBar.add(menu);

		// week chooser
		Object[] weekModel = new Object[17];
		for( int i = 0; i < weekModel.length; i++ )
		{
			weekModel[i] = new WeekEntry( new Integer(i+1), this.poolService);
		}
		m_weekChooser = new NumberedComboBox(weekModel);
		m_weekChooser.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e )
			{
				if( Pool.this.picksChooser.isVisible() )
					updatePicksChooser();

				Pool.this.currentView.setModel(
					getCurrentWeek(), getCurrentPicks());
				refreshView();
			}
		});

		this.picksChooser = new JComboBox();
		this.picksChooser.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e )
			{
				Pool.this.currentView.setModel(
					getCurrentWeek(), getCurrentPicks());
				refreshView();
			}
		});
	}


	//-------------------------------------------------------------------------
	// Nested top-level class WeekEntry
	//-------------------------------------------------------------------------

	/**
	 * A wrapper for a week (represented by an int) that can be used within a
	 * JComboBox.
	 */
	private static class WeekEntry
	{
		private PoolService poolService;

		private Integer weekId;
		private Week week;

		private String description;

		private WeekEntry( Integer weekId, PoolService poolService )
		{
			this.weekId = weekId;
			this.poolService = poolService;

			this.description = "WEEK "+this.weekId;
		}

		synchronized private Week getWeek()
		{
			if( this.week == null )
			{
				this.week = this.poolService.findWeekById(this.weekId);
			}

			return this.week;
		}

		public String toString() { return this.description; }

	}	// End of WeekEntry


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public void start()
	{
		JPanel p = new JPanel( new BorderLayout());
		p.add(m_weekChooser, BorderLayout.WEST);
		p.add(this.picksChooser, BorderLayout.EAST);
		getContentPane().add(p, BorderLayout.NORTH);

		m_mainPanel = new JPanel( new BorderLayout());
		getContentPane().add(m_mainPanel, BorderLayout.CENTER);

		setView(this.gameResultsView);

		setVisible(true);
	}

	/**
	 * @return	the currently selected week (as an int from 1-17).
	 */
	public Week getCurrentWeek()
	{
		return ((WeekEntry) m_weekChooser.getSelectedItem()).getWeek();
	}

	public Picks getCurrentPicks()
	{
		return (Picks) this.picksChooser.getSelectedItem();
	}


	//-------------------------------------------------------------------------
	// Views
	//-------------------------------------------------------------------------

	private void setView( View view )
	{
		this.currentView = view;

		this.picksChooser.setVisible(this.currentView.canChoosePlayer());

		if( this.picksChooser.isVisible() )
			updatePicksChooser();

		this.currentView.setModel(getCurrentWeek(), getCurrentPicks());

		m_mainPanel.removeAll();
		m_mainPanel.add(this.currentView.render(), BorderLayout.CENTER);

		refreshView();
	}

	private void updatePicksChooser()
	{
		List<Picks> weekPicks =
			this.poolService.findPicksForWeek(getCurrentWeek());

		this.picksChooser.removeAllItems();

		for( Picks picks : weekPicks )
		{
			this.picksChooser.addItem(picks);
		}

		if( weekPicks.size() > 0 )
			this.picksChooser.setSelectedIndex(0);
	}

	private void refreshView()
	{
		pack();

		invalidate();
		validate();
		repaint();
	}


	//-------------------------------------------------------------------------
	// Actions
	//-------------------------------------------------------------------------

	//-------------------------------------------------------------------------
	// Member class SaveAction
	//-------------------------------------------------------------------------

	/**
	 * Saves the data displayed by the current mode.
	 */
	private class SaveAction extends AbstractAction
	{
		private SaveAction()
		{
			super("Save");

			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
		}

		public void actionPerformed( ActionEvent e )
		{
			// save current view's data
			save();
		}

	}	// End of SaveAction

	/**
	 * Saves the data currently displayed by the view.
	 */
	private boolean save()
	{
		if( this.currentView != null )
			this.currentView.save();
		return true;
	}


	//-------------------------------------------------------------------------
	// Member class Exit
	//-------------------------------------------------------------------------

	/**
	 * Calls <code>exit()</code> to exit the application.
	 */
	private class Exit extends AbstractAction
	{
		private Exit() { super("Exit"); }

		public void actionPerformed( ActionEvent e )
		{
			exit();
		}

	}	// End of Exit

	/**
	 * Exits the application, prompting the user to save the data displayed by
	 * the current view.
	 */
	private void exit()
	{
		// save current view's data; exit
		if( this.currentView == null || this.currentView.exit() )
		{
			// close database connection
			this.poolService.closeConnection();

			System.exit(0);
		}
	}


	//-------------------------------------------------------------------------
	// Member class AddPlayer
	//-------------------------------------------------------------------------

	/**
	 * Prompts user to add a player to the current week.
	 */
	private class AddPlayer extends AbstractAction
	{
		private AddPlayer()
		{
			super("Add Player");
		}

		public void actionPerformed( ActionEvent e )
		{
			Vector v = new Vector(Pool.this.poolService.findAllPlayers());

			if( v.isEmpty() )
			{
				JOptionPane.showMessageDialog(Pool.this, new String[] {
					"No players exist.",
					"Define players using Player | New menu option."
					});
				return;
			}

			Player[] players = new Player[v.size()];
			v.copyInto(players);

			Player player = (Player) JOptionPane.showInputDialog(
									Pool.this,
									"Choose a player", "Choose a Player",
									JOptionPane.INFORMATION_MESSAGE, null,
									players, players[0]);

			if( player != null )
			{
				Week week = getCurrentWeek();

				Picks picks = Pool.this.poolService.findPicksForPlayer(
						week, player, false);

				if( picks != null )
				{
					JOptionPane.showMessageDialog(Pool.this, new String[] {
						player+" already has picks for week "+
						week.getId()+"."});
				}
				else
				{
					picks = Pool.this.poolService.findPicksForPlayer(
						week, player, true);

					List<Picks> weekPicks = new ArrayList<Picks>(
						Pool.this.poolService.findPicksForWeek(week));

					weekPicks.add(picks);

					Collections.sort(weekPicks);

					Pool.this.picksChooser.removeAllItems();

					for( Picks p : weekPicks )
					{
						Pool.this.picksChooser.addItem(p);
					}
				}

				Pool.this.picksChooser.setSelectedItem(picks);
			}
		}

	}	// End of AddPlayer


	//-------------------------------------------------------------------------
	// Member class ChangeView
	//-------------------------------------------------------------------------

	/**
	 * Changes the current view to a specified <code>View</code>.
	 */
	private class ChangeView extends AbstractAction
	{
		private View im_view;

		private ChangeView( String label, View view )
		{
			super(label);

			im_view = view;
		}

		public void actionPerformed( ActionEvent e )
		{
			setView(im_view);
		}

	}	// End of ChangeView


	//-------------------------------------------------------------------------
	// Member class ValidatePicks
	//-------------------------------------------------------------------------

	/**
	 * Validates the currently selected player's picks. Displays errors to the
	 * user.
	 *
	 * @see	PlayerPicksView#validate
	 */
	private class ValidatePicks extends AbstractAction
	{
		private ValidatePicks() { super("Validate Picks"); }

		public void actionPerformed( ActionEvent e )
		{
			if( Pool.this.currentView == Pool.this.playerPicksView )
				Pool.this.playerPicksView.validatePicks();
		}

	}	// End of ValidatePicks

}	// End of Pool
