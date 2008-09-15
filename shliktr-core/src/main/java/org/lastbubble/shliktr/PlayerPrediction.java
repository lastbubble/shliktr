package org.lastbubble.shliktr;

import static org.lastbubble.shliktr.Winner.*;

import java.util.*;

/**
 * @version $Id$
 */
public class PlayerPrediction implements Comparable<PlayerPrediction>
{
	private IPlayer player;

	private Winner[] mustWins;

	private List<String> winningOutcomes;


	//---------------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------------

	public PlayerPrediction( IPlayer player, int gameCnt )
	{
		this.player = player;
		this.mustWins = new Winner[gameCnt];
		this.winningOutcomes = new ArrayList<String>();
	}


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	public IPlayer getPlayer() { return this.player; }

	public List<String> getWinningOutcomes()
	{
		return Collections.unmodifiableList(this.winningOutcomes);
	}

	public void addWinningOutcome( String s )
	{
		if( s != null && s.length() >= this.mustWins.length )
		{
			this.winningOutcomes.add(s);
		}
	}

	public Winner[] getMustWins()
	{
		if( this.winningOutcomes.isEmpty() )
		{
			for( int i = 0; i < this.mustWins.length; i++ )
			{
				this.mustWins[i] = null;
			}

			return this.mustWins;
		}

		// initialize array
		String firstOutcome = this.winningOutcomes.get(0);
		for( int i = 0; i < this.mustWins.length; i++ )
		{
			this.mustWins[i] = (firstOutcome.charAt(i) == '1') ? HOME : AWAY;
		}

		int cnt = this.mustWins.length;

		for( String outcome : this.winningOutcomes )
		{
			for( int i = 0; i < this.mustWins.length; i++ )
			{
				Winner mustWin = this.mustWins[i];
				if( mustWin != null )
				{
					switch( mustWin )
					{
						case HOME:
							if( outcome.charAt(i) != '1' )
							{
								this.mustWins[i] = null;
								cnt--;
							}
							break;
						case AWAY:
							if( outcome.charAt(i) != '0' )
							{
								this.mustWins[i] = null;
								cnt--;
							}
							break;
					}
				}
			}

			if( cnt <= 0 ) break;
		}

		return this.mustWins;
	}


	//---------------------------------------------------------------------------
	// Implements Comparable<PlayerPrediction>
	//---------------------------------------------------------------------------

	public int compareTo( PlayerPrediction pp )
	{
		return pp.winningOutcomes.size() - this.winningOutcomes.size();
	}
}