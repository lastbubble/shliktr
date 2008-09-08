package org.lastbubble.shliktr.model;

/**
 * Computes a player's score for the current week, as well as how close their
 * tiebreaker guess was to the actual result.
 *
 * @version $Id$
 */
public class PlayerScore implements Comparable
{
	private Picks picks;

	private int im_nScore;
	private String im_sRecord;
	private int im_nLost;
	private int im_nRemaining;
	private int im_nTiebreakerDiff;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public PlayerScore( Picks picks )
	{
		this.picks = picks;

		int wins = 0;
		int losses = 0;

		for( int i = 0, cnt = this.picks.size(); i < cnt; i++ )
		{
			Pick pick = this.picks.getPickAt(i);
			int ranking = pick.getRanking();

			if( pick.getGame().getWinner() != null )
			{
				if( pick.isCorrect() )
				{
					im_nScore += ranking;
					wins++;
				}
				else
				{
					im_nLost -= ranking;
					losses++;
				}
			}
			else
			{
				im_nRemaining += ranking;
			}
		}

		im_sRecord = String.valueOf(wins)+"-"+String.valueOf(losses);

		im_nTiebreakerDiff = Math.abs(this.picks.getTiebreaker() -
			this.picks.getWeek().getTiebreakerAnswer());
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	public Player getPlayer() { return this.picks.getPlayer(); }

	public int getScore() { return im_nScore; }

	public String getRecord() { return im_sRecord; }

	public int getLost() { return im_nLost; }

	public int getRemaining() { return im_nRemaining; }

	public int getTiebreaker() { return this.picks.getTiebreaker(); }

	public int getTiebreakerDiff() { return im_nTiebreakerDiff; }

	/**
	 * Implements Comparable (included checking tiebreaker for ties).
	 */
	public int compareTo( Object o )
	{
		PlayerScore result = (PlayerScore) o;

		int delta = result.getScore() - getScore();
		if( delta != 0 )
			return delta;

		// same score, compare tiebreaker
		return (getTiebreakerDiff() - result.getTiebreakerDiff());
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append(getPlayer());
		buf.append(" had ");
		buf.append(getScore());
		buf.append(" points ");
		buf.append('(');
		buf.append(getRecord());
		buf.append(')');
		return buf.toString();
	}

}	// End of PlayerScore
