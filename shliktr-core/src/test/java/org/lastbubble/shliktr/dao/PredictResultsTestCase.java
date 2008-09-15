package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.PlayerPrediction;
import org.lastbubble.shliktr.Winner;
import static org.lastbubble.shliktr.Winner.*;
import static org.lastbubble.shliktr.dao.TestHelper.*;

import java.util.*;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class PredictResultsTestCase extends PoolServiceTestCase
{
	private static Game game1 = new Game();
	private static Game game2 = new Game();

	private static Player player1 = new Player("player1");
	private static Player player2 = new Player("player2");
	private static Player player3 = new Player("player3");

	private static Week week = newWeek(1, Arrays.asList(game1, game2));

	private static PoolEntry entry1 = newEntry(week, player1, Arrays.asList(
			newPick(game1, HOME, 2), newPick(game2, AWAY, 1)
		)
	);

	private static PoolEntry entry2 = newEntry(week, player2, Arrays.asList(
			newPick(game1, AWAY, 1), newPick(game2, HOME, 2)
		)
	);

	private static PoolEntry entry3 = newEntry(week, player3, Arrays.asList(
			newPick(game1, HOME, 1), newPick(game2, HOME, 1)
		)
	);

	private static List<PoolEntry> entriesForWeek =
		Arrays.asList(entry1, entry2, entry3);

	private Map<? extends IPlayer, PlayerPrediction> predictions;


	//---------------------------------------------------------------------------
	// PoolServiceTestCase methods
	//---------------------------------------------------------------------------

	@Override
	protected void onSetUp()
	{
		expect(poolDao.findWeekById(week.getId())).andReturn(week).anyTimes();
		expect(poolDao.findEntriesForWeek(week)).andReturn(entriesForWeek)
			.anyTimes();
		poolDao.refreshGamesForWeek(week);
		replay(poolDao);
	}

	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testSomething() { }

	public void testPredictResults_nullWeek()
	{
		predictions = poolService.predictResults(null, Collections.EMPTY_LIST);
		assertTrue(predictions.isEmpty());
	}

	public void testPredictResults_invalidWinners()
	{
		assertTrue(predictResults(null).isEmpty());
		assertTrue(predictResults(Arrays.asList(HOME)).isEmpty());
	}

	public void testPredictResults_noWinners()
	{
		List<Winner> winners = new ArrayList<Winner>(2);
		winners.add(null);
		winners.add(null);

		predictResults(winners);

		assertPredictionEquals(player1, Arrays.asList("00*", "10", "11*"),
			new Winner[] { null, null });
		assertPredictionEquals(player2, Arrays.asList("00*", "01", "11*"),
			new Winner[] { null, null });
		assertPredictionEquals(player3, Arrays.asList("11*"),
			new Winner[] { HOME, HOME });

		verify(poolDao);
	}

	public void testPredictResults_oneWinner()
	{
		List<Winner> winners = new ArrayList<Winner>(2);
		winners.add(null);
		winners.add(AWAY);

		predictResults(winners);

		assertPredictionEquals(player1, Arrays.asList("0*", "1"),
			new Winner[] { null });
		assertPredictionEquals(player2, Arrays.asList("0*"),
			new Winner[] { AWAY });
		assertFalse(predictions.containsKey(player3));

		verify(poolDao);
	}

	protected Map<? extends IPlayer, PlayerPrediction> predictResults(
		List<Winner> winners )
	{
		predictions = poolService.predictResults(week, winners);
		return predictions;
	}

	protected void assertPredictionEquals(
		Player player, List<String> expectedOutcomes, Winner[] expectedMustWins )
	{
		PlayerPrediction prediction = predictions.get(player);
		assertEquals(expectedOutcomes, prediction.getWinningOutcomes());
		Winner[] mustWins = prediction.getMustWins();
		assertEquals(expectedMustWins.length, mustWins.length);
		for( int i = 0; i < mustWins.length; i++ )
		{
			assertSame(expectedMustWins[i], mustWins[i]);
		}
	}
}