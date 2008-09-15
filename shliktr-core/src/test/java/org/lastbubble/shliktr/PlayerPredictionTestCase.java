package org.lastbubble.shliktr;

import java.util.*;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class PlayerPredictionTestCase extends TestCase
{
	private static IPlayer player = createMock(IPlayer.class);

	private static int gameCnt = 3;

	private PlayerPrediction prediction;


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	protected void setUp()
	{
		prediction = new PlayerPrediction(player, gameCnt);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testDefaults()
	{
		assertSame(player, prediction.getPlayer());
		assertTrue(prediction.getWinningOutcomes().isEmpty());
		assertMustWinsEquals( new Winner[gameCnt]);
	}

	public void testAddWinningOutcome_null()
	{
		assertTrue(prediction.getWinningOutcomes().isEmpty());
		prediction.addWinningOutcome(null);
		assertTrue(prediction.getWinningOutcomes().isEmpty());
	}

	public void testAddWinningOutcome_notEnoughGames()
	{
		assertTrue(prediction.getWinningOutcomes().isEmpty());
		prediction.addWinningOutcome("");
		assertTrue(prediction.getWinningOutcomes().isEmpty());
		prediction.addWinningOutcome("00");
		assertTrue(prediction.getWinningOutcomes().isEmpty());
	}

	public void testAddWinningOutcome()
	{
		List<String> outcomes = Arrays.asList("101", "011");

		assertTrue(prediction.getWinningOutcomes().isEmpty());
		addWinningOutcomes(outcomes);
		assertEquals(outcomes, prediction.getWinningOutcomes());
	}

	public void testGetMustWins()
	{
		addWinningOutcomes(Arrays.asList("101", "011"));
		assertMustWinsEquals( new Winner[] { null, null, Winner.HOME });
	}

	protected void addWinningOutcomes( List<String> outcomes )
	{
		for( String outcome : outcomes )
		{
			prediction.addWinningOutcome(outcome);
		}
	}

	protected void assertMustWinsEquals( Winner[] expected )
	{
		Winner[] mustWins = prediction.getMustWins();
		assertEquals(expected.length, mustWins.length);
		for( int i = 0; i < expected.length; i++ )
		{
			assertSame(expected[i], mustWins[i]);
		}
	}
}