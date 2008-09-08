package org.lastbubble.shliktr.model;

/**
 * @version $Id: StringUtils.java 50 2007-11-20 19:21:56Z eheaton $
 */
public final class StringUtils
{
	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	private StringUtils() { }


	//-------------------------------------------------------------------------
	// Utility methods
	//-------------------------------------------------------------------------

	public static String pad( int n, int pad, boolean right )
	{
		return pad(String.valueOf(n), pad, right);
	}

	public static String pad( String s, int pad, boolean right )
	{
		int len = s.length();

		String padding = "               ";
		if( len < pad )
		{
			padding = padding.substring(0, pad - len);
		}
		else
		{
			padding = "";
		}

		return right ? padding + s : s + padding;
	}

}	// End of StringUtils
