package org.lastbubble.shliktr.ui;

import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;

import java.awt.Component;

/**
 * @version $Id$
 */
public interface View
{
	boolean canChoosePlayer();

	Component render();

	void setModel( IWeek week, IPoolEntry entry );

	void save();

	boolean exit();

}
