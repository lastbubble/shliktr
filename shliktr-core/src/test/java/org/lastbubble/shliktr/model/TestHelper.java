package org.lastbubble.shliktr.model;

/**
 * @version $Id$
 */
public final class TestHelper
{
	public static Team newTeam( String abbr )
	{
		return new Team(abbr);
	}

	public static Game newGame()
	{
		return newGame(newTeam("abc"), newTeam("def"));
	}

	public static Game newGame( Team homeTeam, Team awayTeam )
	{
		Game game = new Game();
		game.setHomeTeam(homeTeam);
		game.setAwayTeam(awayTeam);
		return game;
	}

	public static Week newWeek( int id )
	{
		Week week = new Week();
		week.setId(id);
		return week;
	}

	public static Pick newPick( Game game, Winner winner, int ranking )
	{
		Pick pick = new Pick();
		pick.setGame(game);
		pick.setWinner(winner);
		pick.setRanking(ranking);
		return pick;
	}
}