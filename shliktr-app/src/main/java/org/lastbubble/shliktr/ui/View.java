package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.model.Picks;
import org.lastbubble.shliktr.model.Week;

import java.awt.Component;

/**
 * @version $Id$
 */
public interface View
{
	boolean canChoosePlayer();

	Component render();

	void setModel( Week week, Picks picks );

	void save();

	boolean exit();

}	// End of View
