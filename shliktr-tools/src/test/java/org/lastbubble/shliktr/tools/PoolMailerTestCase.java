package org.lastbubble.shliktr.tools;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.mail.IPoolMailer;
import org.lastbubble.shliktr.service.PoolService;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class PoolMailerTestCase extends TestCase
{
	private static PoolService poolService = createMock(PoolService.class);

	private static IPoolMailer poolMailer = createMock(IPoolMailer.class);

	private PoolMailer mailer;


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		reset(poolService);
		reset(poolMailer);

		mailer = new PoolMailer();
		mailer.setPoolService(poolService);
		mailer.setPoolMailer(poolMailer);
	}

	@Override
	protected void tearDown()
	{
		verify(poolService, poolMailer);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testMail() throws Exception
	{
		String username = "user";
		Integer weekNumber = 1;

		IPlayer player = createMock(IPlayer.class);
		IWeek week = createMock(IWeek.class);
		IPoolEntry entry = createMock(IPoolEntry.class);

		expect(poolService.findPlayerByUsername(username)).andReturn(player);
		expect(poolService.findWeekById(weekNumber)).andReturn(week);
		expect(poolService.findEntry(week, player, false)).andReturn(entry);
		replay(poolService);

		poolMailer.mailEntry(entry);
		replay(poolMailer);

		mailer.mail(username, weekNumber);

		verify(poolService, poolMailer);
	}
}