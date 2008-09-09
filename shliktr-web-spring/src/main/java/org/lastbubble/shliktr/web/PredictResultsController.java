package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.model.Game;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.PlayerPrediction;
import org.lastbubble.shliktr.model.Week;
import org.lastbubble.shliktr.model.Winner;
import org.lastbubble.shliktr.service.PoolService;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * @version $Id$
 */
public class PredictResultsController extends AbstractController
{
	private PoolService poolService;


	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public void setPoolService( PoolService ps ) { this.poolService = ps; }


	//-------------------------------------------------------------------------
	// AbstractController methods
	//-------------------------------------------------------------------------

	@Override
	protected ModelAndView handleRequestInternal(
		HttpServletRequest request, HttpServletResponse response )
	throws Exception
	{
		Map model = new HashMap();

		int weekId = 1;

		String s = request.getParameter("weekId");
		if( s != null && s.length() > 0 )
		{
			try { weekId = Integer.parseInt(s); }
			catch( NumberFormatException e ) { }
		}

		Week week = this.poolService.findWeekById(weekId);
		model.put("week", week);

		Player player = WebUtils.getPlayerFromRequest(request);
		model.put("player", player);

		List<Prediction> predictions = new ArrayList<Prediction>();
		model.put("predictions", predictions);

		List<Game> games = week.getGames();
		List<Winner> winners = new ArrayList<Winner>(games.size());
		List<Game> unfinished = new ArrayList<Game>(games.size());

		for( Game game : games )
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

		return new ModelAndView("predictResults", model);
	}


	//-------------------------------------------------------------------------
	// Nested top-level class Prediction
	//-------------------------------------------------------------------------

	public static class Prediction
	{
		private Player player;
		private int outcomeCnt;
		private List<String> outcomes;
		private String mustWins;

		public Prediction(
			Player player, int outcomeCnt, List<String> outcomes, String mustWins )
		{
			this.player = player;
			this.outcomeCnt = outcomeCnt;
			this.outcomes = outcomes;
			this.mustWins = mustWins;
		}

		public static Prediction create(
			PlayerPrediction playerPrediction, List<Game> games )
		{
			Player player = playerPrediction.getPlayer();

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
					Game game = games.get(i);

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

		public static String describeOutcome( String outcome, List<Game> games )
		{
			StringBuilder buf = new StringBuilder();

			int cnt = Math.min(outcome.length(), games.size());
			for( int i = 0; i < cnt; i++ )
			{
				if( buf.length() > 0 ) buf.append(", ");

				Game game = games.get(i);
				buf.append(outcome.charAt(i) == '1' ?
					game.getHomeTeam().getAbbr().toUpperCase() :
					game.getAwayTeam().getAbbr().toUpperCase()
				);
			}

			return buf.toString();
		}

		public Player getPlayer() { return this.player; }

		public int getOutcomeCount() { return this.outcomeCnt; }

		public List<String> getOutcomes() { return this.outcomes; }

		public String getMustWins() { return this.mustWins; }
	}

}
