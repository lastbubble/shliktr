package org.lastbubble.shliktr.dao;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public abstract class PoolServiceTestCase extends TestCase
{
	protected static PoolDao poolDao = createMock(PoolDao.class);

	protected static PoolServiceDaoImpl poolService = new PoolServiceDaoImpl(poolDao);


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	protected final void setUp()
	{
		reset(poolDao);

		onSetUp();
	}

	/** Template method */
	protected void onSetUp() { }

}