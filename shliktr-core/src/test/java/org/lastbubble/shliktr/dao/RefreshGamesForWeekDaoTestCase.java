package org.lastbubble.shliktr.dao;

import java.util.List;

/**
 * @version $Id$
 */
public class RefreshGamesForWeekDaoTestCase extends DaoTestCase
{
	static {
		createDatabase(createDataSetResource(
				"org/lastbubble/shliktr/dao/week.xml"
			)
		);
	}

	private Week week;


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testRefreshGamesForWeek()
	{
		week = poolDao.findWeekById(1);

		List<Game> games = week.getGames();

		assertScoreEquals(games.get(0), 14, 7);
		assertScoreEquals(games.get(1), 0, 0);

		setScore(games.get(0), 21, 10);
		setScore(games.get(1), 17, 35);

		assertScoreEquals(games.get(0), 21, 10);
		assertScoreEquals(games.get(1), 17, 35);

		poolDao.refreshGamesForWeek(week);

		assertScoreEquals(games.get(0), 14, 7);
		assertScoreEquals(games.get(1), 0, 0);
	}

	protected void setScore( Game game, int homeScore, int awayScore )
	{
		game.setHomeScore(homeScore);
		game.setAwayScore(awayScore);
	}

	protected void assertScoreEquals( Game game, int homeScore, int awayScore )
	{
		assertEquals(homeScore, game.getHomeScore());
		assertEquals(awayScore, game.getAwayScore());
	}

}