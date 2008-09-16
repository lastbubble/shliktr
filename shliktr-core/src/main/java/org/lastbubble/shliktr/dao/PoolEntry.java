package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IPoolEntry;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A player's picks for a specified week's games. Includes the player's
 * tiebreaker guess for that week.
 *
 * @version $Id$
 */
@Entity
@Table(name = "entry")
public class PoolEntry implements IPoolEntry
{
	private Integer id;

	private Week week;

	private Player player;

	private List<Pick> picks;

	private int tiebreaker;

	private int score;

	private int gamesWon;

	private int gamesLost;

	private int lost;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	PoolEntry() { }

	PoolEntry( Week week, Player player )
	{
		this.week = week;
		this.player = player;

		List<Game> games = week.getGames();

		int cnt = games.size();

		List<Pick> picks = new ArrayList<Pick>(cnt);

		for( int i = 0; i < cnt; i++ )
		{
			Pick pick = new Pick(this);
			pick.setGame(games.get(i));
			picks.add(pick);
		}

		this.picks = picks;
	}


	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	Integer getId() { return this.id; }
	void setId( Integer n ) { this.id = n; }

	/** @see	IPoolEntry#getWeek */
	@ManyToOne
	@JoinColumn(nullable = false)
	public Week getWeek() { return this.week; }
	void setWeek( Week w ) { this.week = w; }

	/** @see	IPoolEntry#getPlayer */
	@ManyToOne
	@JoinColumn(nullable = false)
	public Player getPlayer() { return this.player; }
	void setPlayer( Player p ) { this.player = p; }

	/** @see	IPoolEntry#getPicks */
	@OneToMany(mappedBy="entry", cascade = {CascadeType.ALL})
	public List<Pick> getPicks()
	{
		return Collections.unmodifiableList(this.picks);
	}
	void setPicks( List<Pick> l ) { this.picks = l; }

	/** @see	IPoolEntry#getTiebreaker */
	@Column
	public int getTiebreaker() { return this.tiebreaker; }

	/** @see	IPoolEntry#setTiebreaker */
	public void setTiebreaker( int n ) { this.tiebreaker = n; }

	/** @see	IPoolEntry#computeScore */
	public void computeScore()
	{
		this.score = 0;
		this.lost = 0;
		this.gamesWon = 0;
		this.gamesLost = 0;

		for( Pick pick : picks )
		{
			if( pick.getGame().getWinner() != null )
			{
				int ranking = pick.getRanking();
				if( pick.isCorrect() )
				{
					this.score += ranking;
					this.gamesWon++;
				}
				else
				{
					this.lost += ranking;
					this.gamesLost++;
				}
			}
		}
	}

	/** @see	IPoolEntry#getScore */
	@Column
	public int getScore() { return this.score; }
	void setScore( Integer n ) { this.score = (n != null) ? n : 0; }

	/** @see	IPoolEntry#getGamesWon */
	@Column(name = "games_won")
	public int getGamesWon() { return this.gamesWon; }
	void setGamesWon( Integer n ) { this.gamesWon = (n != null) ? n : 0; }

	/** @see	IPoolEntry#getGamesLost */
	@Column(name = "games_lost")
	public int getGamesLost() { return this.gamesLost; }
	void setGamesLost( Integer n ) { this.gamesLost = (n != null) ? n : 0; }

	/** @see	IPoolEntry#getLost */
	@Column
	public int getLost() { return this.lost; }
	void setLost( Integer n ) { this.lost = (n != null) ? n : 0; }

	/** @see	IPoolEntry#getRemaining */
	@Transient
	public int getRemaining()
	{
		int pickCnt = this.picks.size();
		int total = (pickCnt * (pickCnt + 1)) / 2;
		return total - getScore() - getLost();
	}

	/** @see	IPoolEntry#getTiebreakerDiff */
	@Transient
	public int getTiebreakerDiff()
	{
		return Math.abs(getTiebreaker() - getWeek().getTiebreakerAnswer());
	}

	/**
	 * Validates the player's picks for the given week.
	 */
	public boolean validate( String[] errMsg )
	{
		int cnt = this.picks.size();

		if( cnt != getWeek().getGames().size() )
		{
			String s = getPlayer()+" has wrong number of picks";
			System.err.println(s);
			if( errMsg != null ) errMsg[0] = s;
			return false;
		}

		// create an array of the player's picks for the week

		List<Pick> sortedPicks = new ArrayList<Pick>(cnt);
		sortedPicks.addAll(this.picks);

		// sort the array of picks by ranking (in ascending order)

		Collections.sort(sortedPicks, new Comparator<Pick>() {
			public int compare( Pick p1, Pick p2 )
			{
				int ranking1 = Math.max(0, p1.getRanking());
				int ranking2 = Math.max(0, p2.getRanking());
				return ranking1 - ranking2;
			}
		});

		// verify that each pick's ranking is valid;
		// the list of rankings should be {1, 2, 3, ..., cnt}

		boolean foundInvalid = false;

		for( int i = 0; i < cnt; i++ )
		{
			Pick pick = sortedPicks.get(i);

			int ranking = pick.getRanking();

			if( ranking > (i + 1) )
			// if the ranking at the current position is larger than what
			// it should be, then it's possible for the player to have a
			// weekly score that is higher than the possible (which is not
			// fair). this is a fatal error for validation
			{
				String s = getPlayer()+" has illegal ranking for "+pick;
				System.err.println(s);
				if( errMsg != null ) errMsg[0] = s;
				return false;
			}
			else if( ranking != (i + 1) )
			// if the ranking is not what we would expect, then it's too low
			// (which is certainly legal)
			{
				String s = getPlayer()+" has invalid ranking for "+pick;

				if( foundInvalid == false )
				// only print the first occurrence, since if the player missed
				// a ranking, it would have a cascading effect for subsequent
				// picks' rankings
				{
					System.err.println(s);
					foundInvalid = true;
				}

				if( errMsg != null )
				// immediately show validation error to user by returning
				{
					errMsg[0] = s;
					return false;
				}
			}
		}

		return true;
	}

	public int hashCode()
	{
		return getWeek().hashCode() ^ getPlayer().hashCode();
	}

	public boolean equals( Object obj )
	{
		if( obj == this ) return true;

		if( obj instanceof PoolEntry )
		{
			PoolEntry entry = (PoolEntry) obj;
			return (getWeek().equals(entry.getWeek()) &&
					getPlayer().equals(entry.getPlayer()));
		}

		return false;
	}

	public String toString()
	{
		return new StringBuilder()
			.append(getPlayer().getName())
			.append("'s picks for ")
			.append(getWeek())
			.toString();
	}
}