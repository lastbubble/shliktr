package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PlayerPrediction;
import org.lastbubble.shliktr.Winner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

/**
 * @version $Id$
 */
public class PredictResultsController extends WeekController
{
	//-------------------------------------------------------------------------
	// WeekController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleWeek( ModelAndView modelAndView,
		IWeek week, IPlayer player )
	{
		List<Prediction> predictions = new ArrayList<Prediction>();
		modelAndView.addObject("predictions", predictions);

		List<? extends IGame> games = week.getGames();
		List<Winner> winners = new ArrayList<Winner>(games.size());
		List<IGame> unfinished = new ArrayList<IGame>(games.size());

		for( IGame game : games )
		{
			Winner winner = game.getWinner();
			winners.add(winner);
			if( winner == null )
			{
				unfinished.add(game);
			}
		}

		List<PlayerPrediction> playerPredictions =
			new ArrayList<PlayerPrediction>(this.poolService
				.predictResults(week, winners)
				.values()
			);

		Collections.sort(playerPredictions);

		for( PlayerPrediction playerPrediction : playerPredictions )
		{
			predictions.add(Prediction.create(playerPrediction, unfinished));
		}

		return modelAndView;
	}


	//-------------------------------------------------------------------------
	// Nested top-level class Prediction
	//-------------------------------------------------------------------------

	public static class Prediction
	{
		private IPlayer player;
		private int outcomeCnt;
		private List<String> outcomes;
		private String mustWins;

		public Prediction(
			IPlayer player, int outcomeCnt, List<String> outcomes, String mustWins )
		{
			this.player = player;
			this.outcomeCnt = outcomeCnt;
			this.outcomes = outcomes;
			this.mustWins = mustWins;
		}

		public static Prediction create(
			PlayerPrediction playerPrediction, List<? extends IGame> games )
		{
			IPlayer player = playerPrediction.getPlayer();

			List<String> winningOutcomes = playerPrediction.getWinningOutcomes();
			int outcomeCnt = winningOutcomes.size();

			List<String> outcomes = new ArrayList<String>();
			if( outcomeCnt <= 16 )
			{
				for( String outcome : winningOutcomes )
				{
					outcomes.add(describeOutcome(outcome, games));
				}
			}

			StringBuilder buf = new StringBuilder();

			Winner[] mustWins = playerPrediction.getMustWins();
			for( int i = 0; i < mustWins.length; i++ )
			{
				Winner winner = mustWins[i];
				if( winner != null )
				{
					IGame game = games.get(i);

					if( buf.length() > 0 ) buf.append(", ");

					switch( winner )
					{
						case HOME: buf.append(game.getHomeTeam().getName()); break;
						case AWAY: buf.append(game.getAwayTeam().getName()); break;
					}
				}
			}

			return new Prediction(player, outcomeCnt, outcomes, buf.toString());
		}

		public static String describeOutcome(
			String outcome, List<? extends IGame> games )
		{
			StringBuilder buf = new StringBuilder();

			int cnt = Math.min(outcome.length(), games.size());
			for( int i = 0; i < cnt; i++ )
			{
				if( buf.length() > 0 ) buf.append(", ");

				IGame game = games.get(i);
				buf.append(outcome.charAt(i) == '1' ?
					game.getHomeTeam().getAbbr().toUpperCase() :
					game.getAwayTeam().getAbbr().toUpperCase()
				);
			}

			return buf.toString();
		}

		public IPlayer getPlayer() { return this.player; }

		public int getOutcomeCount() { return this.outcomeCnt; }

		public List<String> getOutcomes() { return this.outcomes; }

		public String getMustWins() { return this.mustWins; }
	}
}