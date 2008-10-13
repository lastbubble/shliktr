package org.lastbubble.shliktr;

import java.util.List;

/**
 * @version $Id$
 */
public interface IPoolBank
{
	List<? extends IPoolAccount> getAccounts();

	int getReserve();
}