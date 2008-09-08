package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.model.Pick;
import org.lastbubble.shliktr.model.Picks;
import org.lastbubble.shliktr.model.PlayerScore;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.service.PoolService;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A <code>View</code> that displays the current player's picks, tiebreaker
 * guess, and score for the current week.
 *
 * @version $Id$
 */
public class PlayerPicksView extends JPanel implements View, DocumentListener
{
	private PoolService poolService;

	// model

	private Picks picks;
	/** whether the view has changed the data */
	private boolean dataChanged;

	// view

	private JPanel picksPanel;
	private PickUI[] pickUIs;
	private JLabel tiebreakerLbl;
	private JTextField tiebreakerFld;
	private JLabel scoreLbl;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public PlayerPicksView( PoolService ps )
	{
		super( new BorderLayout());

		this.poolService = ps;

		initComponents();
	}

	private void initComponents()
	{
		this.pickUIs = new PickUI[16];
		for( int i = 0; i < this.pickUIs.length; i++ )
			this.pickUIs[i] = new PickUI();

		this.picksPanel = new JPanel( new GridLayout(17, 3));
		add(this.picksPanel, BorderLayout.CENTER);

		this.tiebreakerLbl = new JLabel();
		this.tiebreakerLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		this.tiebreakerFld = new JTextField(10);

		this.scoreLbl = new JLabel();
		this.scoreLbl.setHorizontalAlignment(SwingConstants.RIGHT);

		Box bottom = Box.createVerticalBox();

		JPanel p;
		p = new JPanel();
		p.add(this.tiebreakerLbl);
		p.add(this.tiebreakerFld);
		bottom.add(p);
		bottom.add(this.scoreLbl);

		add(bottom, BorderLayout.SOUTH);
	}


	//-------------------------------------------------------------------------
	// Model
	//-------------------------------------------------------------------------

	public boolean hasChanged()
	{
		if( this.dataChanged ) return true;

		for( int i = 0; i < this.pickUIs.length; i++ )
		{
			if( this.pickUIs[i].hasChanged() )
			{
				this.dataChanged = true;
			}
		}

		return this.dataChanged;
	}


	//-------------------------------------------------------------------------
	// View
	//-------------------------------------------------------------------------

	private void updateView()
	{
		this.picksPanel.removeAll();

		if( this.picks == null )
		{
			this.picksPanel.add( new JLabel("No data available."));
			return;
		}

		int pickCnt = this.picks.size();
		for( int i = 0; i < this.pickUIs.length; i++ )
		{
			Pick pick = (i < pickCnt) ? this.picks.getPickAt(i) : null;
			this.pickUIs[i].setPick(pick);
		}

		this.picksPanel.add( new JLabel("AWAY"));
		this.picksPanel.add( new JLabel("HOME"));
		this.picksPanel.add( new JLabel("RANKING"));

		for( int i = 0; i < this.pickUIs.length; i++ )
		{
			PickUI ui = this.pickUIs[i];
			if( ui.getPick() == null ) break;

			this.picksPanel.add(ui.getAwayChooser());
			this.picksPanel.add(ui.getHomeChooser());
			this.picksPanel.add(ui.getRankingChooser());
		}

		this.tiebreakerLbl.setText("Tiebreaker guess:");

		this.tiebreakerFld.getDocument().removeDocumentListener(this);
		this.tiebreakerFld.setText(String.valueOf(this.picks.getTiebreaker()));
		this.tiebreakerFld.getDocument().addDocumentListener(this);

		this.scoreLbl.setText("Total score: "+
			new PlayerScore(this.picks).getScore());

		revalidate();
		repaint();

		this.dataChanged = false;
	}


	//-------------------------------------------------------------------------
	// Actions
	//-------------------------------------------------------------------------

	public void validatePicks()
	{
		if( this.picks != null )
		{
			String[] errMsg = new String[1];
			if(! this.picks.validate(errMsg) )
			{
				JOptionPane.showMessageDialog(this, errMsg);
			}
		}
	}


	//-------------------------------------------------------------------------
	// Implements View
	//-------------------------------------------------------------------------

	public boolean canChoosePlayer() { return true; }

	public Component render() { return this; }

	public void setModel( Week week, Picks picks )
	{
		this.picks = picks;

		updateView();
	}

	public void save()
	{
		if( this.picks != null )
		{
			this.poolService.makePersistentPicks(this.picks);
		}
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


	//---------------------------------------------------------------------
	// Implements DocumentListener
	//---------------------------------------------------------------------

	public void insertUpdate( DocumentEvent e ) { documentUpdated(); }
	public void removeUpdate( DocumentEvent e ) { documentUpdated(); }
	public void changedUpdate( DocumentEvent e ) { documentUpdated(); }

	private void documentUpdated()
	{
		if( this.picks != null )
		{
			try {
				String s = this.tiebreakerFld.getText();
				this.picks.setTiebreaker(Integer.parseInt(s));
				this.dataChanged = true;

			} catch( NumberFormatException e ) { /* ignore */ }
		}
	}

}	// End of PlayerPicksView
