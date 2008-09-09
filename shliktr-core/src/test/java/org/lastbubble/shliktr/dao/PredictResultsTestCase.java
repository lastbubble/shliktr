package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.model.*;
import static org.lastbubble.shliktr.model.Winner.*;

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

	private static Week week = new Week(1, Arrays.asList(game1, game2));

	private static Picks picks1 = new Picks(week, player1, Arrays.asList(
			new Pick(game1, HOME, 2), new Pick(game2, AWAY, 1)
		)
	);

	private static Picks picks2 = new Picks(week, player2, Arrays.asList(
			new Pick(game1, AWAY, 1), new Pick(game2, HOME, 2)
		)
	);

	private static Picks picks3 = new Picks(week, player3, Arrays.asList(
			new Pick(game1, HOME, 1), new Pick(game2, HOME, 1)
		)
	);

	private static List<Picks> picksForWeek =
		Arrays.asList(picks1, picks2, picks3);

	private Map<Player, PlayerPrediction> predictions;


	//---------------------------------------------------------------------------
	// PoolServiceTestCase methods
	//---------------------------------------------------------------------------

	@Override
	protected void onSetUp()
	{
		expect(poolDao.findWeekById(week.getId())).andReturn(week).anyTimes();
		expect(poolDao.findPicksForWeek(week)).andReturn(picksForWeek).anyTimes();
		poolDao.refreshGamesForWeek(week);
		replay(poolDao);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

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

	protected Map<Player, PlayerPrediction> predictResults(
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