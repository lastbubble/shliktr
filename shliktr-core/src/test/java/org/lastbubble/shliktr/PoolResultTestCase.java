package org.lastbubble.shliktr;

import java.util.*;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class PoolResultTestCase extends TestCase
{
	private PoolResult result;

	private int week;

	private IPlayer player;

	private Score score;

	private int gameCnt;

	private int tiebreaker;

	private int tiebreakerDiff;


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		week = 1;
		player = createMock(IPlayer.class);
		score = new Score(2, 3, 4, 5);
		gameCnt = 16;
		tiebreaker = 6;
		tiebreakerDiff = 7;

		result = new PoolResult(week, player, score, gameCnt, tiebreaker,
			tiebreakerDiff);
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testProperties()
	{
		assertEquals(week, result.getWeek());
		assertSame(player, result.getPlayer());
		assertEquals(score.getPoints(), result.getPoints());
		assertEquals(score.getGamesWon(), result.getGamesWon());
		assertEquals(score.getGamesLost(), result.getGamesLost());
		assertEquals(score.getPointsLost(), result.getPointsLost());
		assertEquals(tiebreaker, result.getTiebreaker());
		assertEquals(tiebreakerDiff, result.getTiebreakerDiff());
	}

	public void testGetPointsRemaining()
	{
		int totalPoints = (gameCnt * (gameCnt + 1)) / 2;
		int remaining = totalPoints - score.getPoints() - score.getPointsLost();
		assertEquals(remaining, result.getPointsRemaining());
	}

	public void testCompare()
	{
		Score score1 = new Score(1, 2, 3, 4);
		PoolResult result1 = new PoolResult(week, player, score1, 0, 5, 1);

		Score score2 = new Score(5, 6, 7, 8);
		PoolResult result2 = new PoolResult(week, player, score2, 0, 7, 3);
		PoolResult result3 = new PoolResult(week, player, score2, 0, 6, 2);

		List<PoolResult> results = new ArrayList<PoolResult>(Arrays.asList(
				result1, result2, result3
			)
		);

		Collections.sort(results);
		assertEquals(Arrays.asList(result3, result2, result1), results);
	}
}