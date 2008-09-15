package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.ITeam;
import org.lastbubble.shliktr.Winner;
import static org.lastbubble.shliktr.Winner.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

/**
 * A UI for viewing a <code>Pick</code> and setting the predicted winner of the
 * pick's game, as well as the player's ranking for the pick.
 *
 * @see	Pick
 *
 * @version $Id$
 */
public class PickUI implements ActionListener
{
	// model

	private IPick pick;

	// components

	protected PickButton awayBtn;
	protected PickButton homeBtn;
	private JComboBox rankingChooser;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	/**
	 * Creates a <code>PickUI</code>.
	 */
	public PickUI()
	{
		this.awayBtn = new PickButton(AWAY);
		this.awayBtn.addActionListener(this);

		this.homeBtn = new PickButton(HOME);
		this.homeBtn.addActionListener(this);

		this.rankingChooser = new NumberedComboBox(16);
		this.rankingChooser.addActionListener(this);
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	/** @return	the component used to choose the away team. */
	public Component getAwayChooser() { return this.awayBtn; }

	/** @return	the component used to choose the home team. */
	public Component getHomeChooser() { return this.homeBtn; }

	/** @return	the component used to choose the ranking. */
	public Component getRankingChooser() { return this.rankingChooser; }

	/** @return	the pick tracked by the UI. Can be null. */
	public IPick getPick() { return this.pick; }

	/** Sets the pick tracked by the UI. */
	public void setPick( IPick pick )
	{
		this.pick = pick;

		this.awayBtn.setPick(pick);
		this.homeBtn.setPick(pick);

		this.rankingChooser.setSelectedItem((pick != null) ?
			new Integer(pick.getRanking()) : new Integer(0));
		//this.rankingChooser.setEnabled(pick != null && (! pick.isComplete()));
	}

	public boolean hasChanged() { return (getPick() != null); }

	/**
	 * Implements ActionListener
	 */
	public void actionPerformed( ActionEvent e )
	{
		IPick pick = getPick();

		if( pick == null ) return;

		Object source = e.getSource();

		if( source == this.rankingChooser )
		{
			Integer value = (Integer) this.rankingChooser.getSelectedItem();
			pick.setRanking((value != null) ? value.intValue() : 0);
		}
		else if( source instanceof PickButton )
		{
			PickButton btn = (PickButton) source;

			Winner winner = btn.getWinner();
			pick.setWinner((winner == pick.getWinner()) ? null : winner);

			this.awayBtn.updateView();
			this.homeBtn.updateView();
		}
	}


	//---------------------------------------------------------------------
	// Nested top-level class PickButton
	//---------------------------------------------------------------------

	/**
	 * A UI for choosing a team to win a game.
	 */
	private static class PickButton extends JButton
	{
		private Winner winner;
		private IPick pick;

		private PickButton( Winner winner )
		{
			this.winner = winner;

			setModel( new PickButtonModel());

			setHorizontalAlignment(SwingConstants.LEADING);
		}

		private IGame getGame()
		{
			return (this.pick != null) ? this.pick.getGame() : null;
		}

		private Winner getWinner() { return this.winner; }

		private void setPick( IPick pick )
		{
			this.pick = pick;

			updateView();

			ITeam team = null;

			if( this.pick != null )
			{
				IGame game = getGame();
				team = (this.winner == AWAY) ?
					game.getAwayTeam() : game.getHomeTeam();
			}

			setText((team != null) ? team.getLocation() : "");
			setIcon((team != null) ? TeamUI.getTeamIcon(team) : null);
		}

		private void updateView()
		{
			IGame game = getGame();

			setForeground((game != null && game.getWinner() == this.winner) ?
				Color.red : Color.black);
			setBackground(
				(this.pick != null && this.pick.getWinner() == this.winner) ?
					Color.green : Color.white);
		}

		//-----------------------------------------------------------------
		// Member class PickButtonModel
		//-----------------------------------------------------------------

		private class PickButtonModel extends DefaultButtonModel
		{
			public void setArmed( boolean b )
			{
/*
				if( PickButton.this.pick != null &&
					PickButton.this.pick.isComplete() )
				{
					return;
				}
*/
				super.setArmed(b);
			}

			public boolean isArmed()
			{
/*
				if( PickButton.this.pick != null &&
					PickButton.this.pick.isComplete() )
				{
					return false;
				}
*/
				return super.isArmed();
			}

		}	// End of PickButtonModel

	}	// End of PickButton

}	// End of PickUI
