package org.lastbubble.shliktr.ui;

import java.awt.event.*;
import javax.swing.*;

/**
 * A JComboBox that allows selecting items by keying their index (either zero-
 * or one-based) in the list.
 *
 * @version $Id$
 */
public class NumberedComboBox extends JComboBox
{
	/**
	 * Whether the number keyed for an item should be treated as a zero-based
	 * (as opposed to one-based) index.
	 */
	private boolean m_bZeroBased;


	//-------------------------------------------------------------------------
	// Constructors
	//-------------------------------------------------------------------------

	/**
	 * Creates a list of items from 0 to max. Uses zero-based indexes.
	 */
	public NumberedComboBox( int max )
	{
		super(createIntegerArray(max));

		m_bZeroBased = true;
	}

	/**
	 * Uses one-based indexes to select from the given list of items.
	 */
	public NumberedComboBox( Object[] items )
	{
		super(items);
	}


	//-------------------------------------------------------------------------
	// Initialization methods
	//-------------------------------------------------------------------------

	/**
	 * @return	an array of <code>Integer</codes>s from 0 to <code>max</code>.
	 */
	private static Integer[] createIntegerArray( int max )
	{
		Integer[] ia = new Integer[max+1];
		for( int i = 0, len = ia.length; i < len; i++ )
			ia[i] = new Integer(i);

		return ia;
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	/**
	 * Overrides <code>JComboBox</code> to use a custom KeySelectionManager for
	 * keying the index of an item in the list.
	 */
	protected KeySelectionManager createDefaultKeySelectionManager()
	{
		return new NumberedKeySelectionManager();
	}


	//-------------------------------------------------------------------------
	// Member class NumberedKeySelectionManager
	//-------------------------------------------------------------------------

	/**
	 * A custom <code>KeySelectionManager</code> that allows selecting an item
	 * based on its index in a <code>JComboBox</code>'s list of items.
	 */
	private class NumberedKeySelectionManager implements KeySelectionManager
	{
		private Timer im_timer;
		private boolean im_bTeen;

		private NumberedKeySelectionManager()
		{
			im_timer = new Timer(750, new ActionListener() {
				public void actionPerformed( ActionEvent e )
				{
					im_bTeen = false;
				}
			});
			im_timer.setRepeats(true);
			im_timer.start();
		}

		public int selectionForKey( char aKey, ComboBoxModel aModel )
		{
			im_timer.restart();

			// not a number
			if( aKey < '0' || aKey > '9' ) return -1;

			int index = -1;

			index = (aKey - '0');

			if( im_bTeen ) index += 10;

			if( aKey == '1' ) im_bTeen = true;

			if(! m_bZeroBased ) index--;

			return (index < aModel.getSize()) ? index : -1;
		}

	}	// End of NumberedKeySelectionManager

}	// End of NumberedComboBox
