package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.Winner;
import static org.lastbubble.shliktr.Winner.*;

import java.beans.PropertyEditorSupport;

/**
 * @version $Id$
 */
public class WinnerEditor extends PropertyEditorSupport
{
	public void setAsText( String text ) throws IllegalArgumentException
	{
		if( text == null || text.length() == 0 )
		{
			setValue(null);
		}
		else if( text.equalsIgnoreCase("home") )
		{
			setValue(HOME);
		}
		else if( text.equalsIgnoreCase("away") )
		{
			setValue(AWAY);
		}
		else
		{
			throw new IllegalArgumentException("Could not parse winner: "+text);
		}
	}

	public String getAsText()
	{
		Winner winner = (Winner) getValue();
		return (winner != null) ? winner.name().toLowerCase() : "";
	}
}