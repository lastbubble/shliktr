package org.lastbubble.shliktr;

import java.util.*;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class ScoreTestCase extends TestCase
{
	private Score score;


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		score = new Score();
	}


	//---------------------------------------------------------------------------
	// Custom assert
	//---------------------------------------------------------------------------

	protected void assertScoreEquals(
		int points, int gamesWon, int gamesLost, int pointsLost )
	{
		assertEquals(points, score.getPoints());
		assertEquals(gamesWon, score.getGamesWon());
		assertEquals(gamesLost, score.getGamesLost());
		assertEquals(pointsLost, score.getPointsLost());
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testDefaults()
	{
		assertScoreEquals(0, 0, 0, 0);
	}

	public void testCreate()
	{
		int points = 1;
		int gamesWon = 2;
		int gamesLost = 3;
		int pointsLost = 4;
		score = new Score(points, gamesWon, gamesLost, pointsLost);
		assertScoreEquals(points, gamesWon, gamesLost, pointsLost);
	}

	public void testSetPoints()
	{
		int n = 23;
		score.setPoints(n);
		assertEquals(n, score.getPoints());
	}

	public void testSetGamesWon()
	{
		int n = 23;
		score.setGamesWon(n);
		assertEquals(n, score.getGamesWon());
	}

	public void testGamesLost()
	{
		int n = 23;
		score.setGamesLost(n);
		assertEquals(n, score.getGamesLost());
	}

	public void testSetPointsLost()
	{
		int n = 23;
		score.setPointsLost(n);
		assertEquals(n, score.getPointsLost());
	}

	public void testClear()
	{
		score.setPoints(1);
		score.setGamesWon(1);
		score.setGamesLost(1);
		score.setPointsLost(1);

		score.clear();

		assertScoreEquals(0, 0, 0, 0);
	}

	public void testAdd_correct()
	{
		int ranking1 = 23;
		int ranking2 = 45;

		IPick pick1 = createMock(IPick.class);
		expect(pick1.isCorrect()).andReturn(true);
		expect(pick1.getRanking()).andReturn(ranking1);

		IPick pick2 = createMock(IPick.class);
		expect(pick2.isCorrect()).andReturn(true);
		expect(pick2.getRanking()).andReturn(ranking2);

		replay(pick1, pick2);

		score.add(pick1);
		assertScoreEquals(ranking1, 1, 0, 0);

		score.add(pick2);
		assertScoreEquals(ranking1 + ranking2, 2, 0, 0);
	}

	public void testAdd_incorrect()
	{
		int ranking1 = 23;
		int ranking2 = 45;

		IPick pick1 = createMock(IPick.class);
		expect(pick1.isCorrect()).andReturn(false);
		expect(pick1.getRanking()).andReturn(ranking1);

		IPick pick2 = createMock(IPick.class);
		expect(pick2.isCorrect()).andReturn(false);
		expect(pick2.getRanking()).andReturn(ranking2);

		replay(pick1, pick2);

		score.add(pick1);
		assertScoreEquals(0, 0, 1, ranking1);

		score.add(pick2);
		assertScoreEquals(0, 0, 2, ranking1 + ranking2);
	}

	public void testCompare()
	{
		Score score1 = new Score();
		score1.setPoints(23);
		Score score2 = new Score();
		score2.setPoints(42);

		assertTrue(score1.compareTo(score2) > 0);

		List<Score> scores = new ArrayList<Score>(Arrays.asList(score1, score2));
		Collections.sort(scores);
		assertEquals(Arrays.asList(score2, score1), scores);

		Score score3 = new Score();
		score3.setPoints(score2.getPoints());
		assertEquals(0, score2.compareTo(score3));
	}
}