package org.lastbubble.shliktr;

/**
 * @version $Id$
 */
public final class ShliktrUtils
{
	private ShliktrUtils() { }

	public static boolean nullSafeEquals( Object a, Object b )
	{
		return (a == null || b == null) ? (a == b) : a.equals(b);
	}
}