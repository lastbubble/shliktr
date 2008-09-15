package org.lastbubble.shliktr;

/**
 * @version $Id$
 */
public abstract class PickBase implements IPick
{
	private Winner winner;

	private int ranking;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	protected PickBase() { }


	//-------------------------------------------------------------------------
	// Implements IPick
	//-------------------------------------------------------------------------

	/** @see	IPick#getGame */
	abstract public IGame getGame();

	/** @see	IPick#getWinner */
	public Winner getWinner() { return this.winner; }

	/** @see	IPick#setWinner */
	public void setWinner( Winner winner ) { this.winner = winner; }

	/** @see	IPick#getTeam */
	public ITeam getTeam()
	{
		ITeam team = null;

		Winner winner = getWinner();
		if( winner != null )
		{
			switch( winner )
			{
				case HOME: team = getGame().getHomeTeam(); break;
				case AWAY: team = getGame().getAwayTeam(); break;
			}
		}

		return team;
	}

	/** @see	IPick#isCorrect */
	public boolean isCorrect()
	{
		Winner winner = getWinner();
		return (winner != null && winner == getGame().getWinner());
	}

	/** @see	IPick#getRanking */
	public int getRanking() { return this.ranking; }

	/** @see	IPick#setRanking */
	public void setRanking( int n ) { this.ranking = n; }


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	public boolean equals( Object obj )
	{
		if( obj == this ) return true;

		if( obj instanceof IPick )
		{
			if( getGame() == null || getWinner() == null ) return false;

			IPick pick = (IPick) obj;

			return getGame().equals(pick.getGame())
				&& getWinner() == pick.getWinner()
				&& getRanking() == pick.getRanking();
		}

		return false;
	}

	public int hashCode()
	{
		int hashCode = getRanking();
		if( getGame() != null )
			hashCode += 17 * getGame().hashCode();
		if( getWinner() != null )
			hashCode += 37 * getWinner().hashCode();
		return hashCode;
	}

	public String toString()
	{
		StringBuilder buf = new StringBuilder();

		IGame game = getGame();
		Winner winner = getWinner();

		if( game != null && winner != null )
		{
			switch( winner )
			{
				case HOME: buf.append(game.getHomeTeam()); break;
				case AWAY: buf.append(game.getAwayTeam()); break;
			}
		}
		else
		{
			buf.append("<none>");
		}

		return buf.append(" for ").append(getRanking()).toString();
	}
}