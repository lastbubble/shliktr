package org.lastbubble.shliktr.mail;

import org.lastbubble.shliktr.*;
import static org.lastbubble.shliktr.Winner.*;

import java.util.*;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class MessageFactoryImplTestCase extends TestCase
{
	private IMessageFactory messageFactory;


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		messageFactory = new MessageFactoryImpl();
	}

	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testMessageForEntry() throws Exception
	{
		String playerName = "Player";
		int weekNumber = 1;
		String tiebreakerQuestion = "Tiebreaker?";
		int tiebreakerGuess = 23;

		IPlayer player = createMock(IPlayer.class);
		expect(player.getName()).andReturn(playerName).anyTimes();
		replay(player);

		IWeek week = createMock(IWeek.class);
		expect(week.getWeekNumber()).andReturn(weekNumber).anyTimes();
		expect(week.getTiebreaker()).andReturn(tiebreakerQuestion).anyTimes();
		replay(week);

		List<? extends IPick> picks = Arrays.asList(
			newPick("A", "B", HOME, 3),
			newPick("C", "D", AWAY, 2),
			newPick("E", "F", HOME, 1)
		);

		IPoolEntry entry = new PoolEntryMock(week, player, picks, tiebreakerGuess);

		String message = messageFactory.messageForEntry(entry);
		assertNotNull(message);
		assertTrue(message.startsWith(playerName));
		assertTrue(message.indexOf("A at B") > -1);
		assertTrue(message.indexOf("<b>B</b>") > -1);
		assertTrue(message.indexOf("C at D") > -1);
		assertTrue(message.indexOf("<b>C</b>") > -1);
		assertTrue(message.indexOf("E at F") > -1);
		assertTrue(message.indexOf("<b>F</b>") > -1);

		String s = new StringBuilder()
			.append(tiebreakerQuestion)
			.append(' ')
			.append("<b>")
			.append(tiebreakerGuess)
			.append("</b>")
			.toString();
		assertTrue(message.indexOf(s) > -1);
	}


	//---------------------------------------------------------------------------
	// Helper methods
	//---------------------------------------------------------------------------

	public static IPick newPick(
		String awayLocation, String homeLocation, Winner winner, int ranking )
	{
		ITeam awayTeam = createMock(ITeam.class);
		expect(awayTeam.getLocation()).andReturn(awayLocation).anyTimes();
		replay(awayTeam);

		ITeam homeTeam = createMock(ITeam.class);
		expect(homeTeam.getLocation()).andReturn(homeLocation).anyTimes();
		replay(homeTeam);

		IGame game = createMock(IGame.class);
		expect(game.getAwayTeam()).andReturn(awayTeam).anyTimes();
		expect(game.getHomeTeam()).andReturn(homeTeam).anyTimes();
		replay(game);

		IPick pick = createMock(IPick.class);
		expect(pick.getGame()).andReturn(game).anyTimes();
		expect(pick.getTeam()).andReturn(winner == HOME ? homeTeam : awayTeam)
			.anyTimes();
		expect(pick.getRanking()).andReturn(ranking).anyTimes();
		replay(pick);

		return pick;
	}
}