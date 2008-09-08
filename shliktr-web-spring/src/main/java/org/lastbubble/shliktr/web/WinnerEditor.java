package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.Winner;

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
			setValue(Winner.HOME);
		}
		else if( text.equalsIgnoreCase("away") )
		{
			setValue(Winner.AWAY);
		}
		else
		{
			throw new IllegalArgumentException(
				"Could not parse winner: " + text);
		}
	}

	public String getAsText()
	{
		Winner winner = (Winner) getValue();
		return (winner != null) ? winner.getValue() : "";
	}

}	// End of WinnerEditor
