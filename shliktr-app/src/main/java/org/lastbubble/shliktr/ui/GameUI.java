package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.model.Game;
import org.lastbubble.shliktr.model.Team;
import org.lastbubble.shliktr.model.Winner;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;

/**
 * A UI for viewing a <code>Game</code> and setting the score of the game.
 *
 * @see	Game
 *
 * @version $Id$
 */
public class GameUI implements DocumentListener
{
	/** The <code>Game</code> used as the UI's model. Can be null. */
	private Game m_game;

	/** The UI for the away team and score. */
	private ScoreUI m_awayUI;
	/** The UI for the home team and score. */
	private ScoreUI m_homeUI;

	protected EventListenerList listenerList = new EventListenerList();


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	/**
	 * Creates a <code>GameUI</code>.
	 */
	public GameUI()
	{
		m_awayUI = new ScoreUI(Winner.AWAY);
		m_awayUI.setDocumentListener(this);
		m_homeUI = new ScoreUI(Winner.HOME);
		m_homeUI.setDocumentListener(this);
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	/** @return	the component used to choose the away team. */
	public Component getAwayChooser() { return m_awayUI; }

	/** @return	the component used to choose the home team. */
	public Component getHomeChooser() { return m_homeUI; }

	/** @return	the <code>Game</code> tracked by the UI. Can be null. */
	public Game getGame() { return m_game; }

	/** Sets the <code>Game</code> tracked by the UI. */
	public void setGame( Game game )
	{
		m_game = game;

		m_awayUI.setGame(m_game);
		m_homeUI.setGame(m_game);

		updateView();
	}

	private void updateView()
	{
		m_awayUI.updateView();
		m_homeUI.updateView();
	}


	//-------------------------------------------------------------------------
	// Implements DocumentListener
	//-------------------------------------------------------------------------

	public void changedUpdate( DocumentEvent e ) { documentChanged(); }

	public void insertUpdate( DocumentEvent e ) { documentChanged(); }

	public void removeUpdate( DocumentEvent e ) { documentChanged(); }

	private void documentChanged()
	{
		Game game = getGame();

		if( game != null )
		{
			game.setAwayScore(m_awayUI.getScore());
			game.setHomeScore(m_homeUI.getScore());

			updateView();

			fireStateChanged();
		}
	}


	//-------------------------------------------------------------------------
	// ChangeListener management
	//-------------------------------------------------------------------------

	public void addChangeListener( ChangeListener l )
	{
		this.listenerList.add(ChangeListener.class, l);
	}

	public void removeChangeListener( ChangeListener l )
	{
		this.listenerList.remove(ChangeListener.class, l);
	}

	protected void fireStateChanged()
	{
		ChangeEvent event = new ChangeEvent(this);

		Object[] listeners = this.listenerList.getListenerList();
		for( int i = listeners.length - 2; i >= 0; i -= 2 )
		{
			if( listeners[i] == ChangeListener.class )
			{
				((ChangeListener) listeners[i + 1]).stateChanged(event);
			}
		}
	}


	//---------------------------------------------------------------------
	// Nested top-level class ScoreUI
	//---------------------------------------------------------------------

	/**
	 * A UI for viewing/modify a team's score in a game.
	 */
	private static class ScoreUI extends JPanel
	{
		private JLabel im_label;
		private JTextField im_field;

		private Winner im_winner;
		private Game im_game;

		private DocumentListener im_listener;

		private ScoreUI( Winner winner )
		{
			super( new BorderLayout());

			im_label = new JLabel();
			im_field = new JTextField(5);

			add(im_label, BorderLayout.CENTER);
			add(im_field, BorderLayout.LINE_END);

			im_winner = winner;
		}

		private Winner getWinner() { return im_winner; }

		private void setDocumentListener( DocumentListener l )
		{
			im_listener = l;
		}

		private void setGame( Game game )
		{
			im_game = game;

			if( im_game != null )
			{
				Team team = (im_winner == Winner.AWAY) ?
					im_game.getAwayTeam() : im_game.getHomeTeam();

				im_label.setText(team.getLocation());
				im_label.setIcon(TeamUI.getTeamIcon(team));

				int score = (im_winner == Winner.AWAY) ?
					im_game.getAwayScore() : im_game.getHomeScore();

				im_field.getDocument().removeDocumentListener(im_listener);
				im_field.setText(String.valueOf(score));
				im_field.getDocument().addDocumentListener(im_listener);
			}
			else
			{
				im_label.setText("");
				im_label.setIcon(null);

				im_field.getDocument().removeDocumentListener(im_listener);
				im_field.setText("");
				im_field.getDocument().addDocumentListener(im_listener);
			}
		}

		private int getScore()
		{
			int score = 0;
			try { score = Integer.parseInt(im_field.getText()); }
			catch( NumberFormatException e ) { }

			return score;
		}

		private void updateView()
		{
			setBackground(
				(im_game != null && im_game.getWinner() == im_winner) ?
					Color.green : null);
		}

	}	// End of ScoreUI

}	// End of GameUI
