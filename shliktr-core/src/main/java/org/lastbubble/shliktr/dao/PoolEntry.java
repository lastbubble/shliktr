package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IGame;
import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.PoolResult;
import org.lastbubble.shliktr.Score;

import java.util.*;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A player's picks for a specified week's games. Includes the player's
 * tiebreaker guess for that week.
 *
 * @version $Id$
 */
@Entity
@Table(name = "entry")
@NamedQueries( {
		@NamedQuery(
			name="entry.findResultsForWeek",
			query="select new org.lastbubble.shliktr.PoolResult("
				+"w.id, "
				+"e.player, "
				+"e.score, "
				+"w.games.size, "
				+"e.tiebreaker, "
				+"abs(w.tiebreakerAnswer - e.tiebreaker)"
				+") "
				+"from PoolEntry as e join e.week as w "
				+"where w.id = :week"
		),
		@NamedQuery(
			name="entry.findAllResults",
			query="select new org.lastbubble.shliktr.PoolResult("
				+"w.id, "
				+"e.player, "
				+"e.score, "
				+"w.games.size, "
				+"e.tiebreaker, "
				+"abs(w.tiebreakerAnswer - e.tiebreaker)"
				+") "
				+"from PoolEntry as e join e.week as w"
		)
	}
)
public class PoolEntry implements IPoolEntry
{
	private Integer id;

	private Week week;

	private Player player;

	private List<Pick> picks;

	private int tiebreaker;

	private Score score = new Score();


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

	@Embedded
	@AttributeOverrides( {
			@AttributeOverride(name="points", column = @Column(name="score")),
			@AttributeOverride(name="gamesWon", column = @Column(name="games_won")),
			@AttributeOverride(name="gamesLost", column = @Column(name="games_lost")),
			@AttributeOverride(name="pointsLost", column = @Column(name="lost"))
		}
	)
	Score getScore() { return this.score; }
	void setScore( Score score ) { this.score = score; }

	/** @see	IPoolEntry#computeResult */
	public PoolResult computeResult()
	{
		return new PoolResult(
			this.week.getWeekNumber(),
			this.player,
			computeScore(),
			this.week.getGames().size(),
			this.tiebreaker,
			Math.abs(week.getTiebreakerAnswer() - this.tiebreaker)
		);
	}

	/** @see	IPoolEntry#computeResult(List<? extends IGame>) */
	public PoolResult computeResult( List<? extends IGame> games )
	{
		return new PoolResult(
			this.week.getWeekNumber(),
			this.player,
			computeScore(games),
			games.size(),
			this.tiebreaker,
			Math.abs(week.getTiebreakerAnswer() - this.tiebreaker)
		);
	}

	/** @see	IPoolEntry#updateScore */
	public void updateScore()
	{
		this.score = computeScore();
	}

	private Score computeScore()
	{
		Score score = new Score();

		for( Pick pick : picks )
		{
			if( pick.getGame().getWinner() != null )
			{
				score.add(pick);
			}
		}

		return score;
	}

	private Score computeScore( List<? extends IGame> games )
	{
		Score score = new Score();

		for( Pick pick : picks )
		{
			int i = games.indexOf(pick.getGame());
			if( i > -1 )
			{
				IGame game = games.get(i);
				score.update(pick.getRanking(), game.getWinner() == pick.getWinner());
			}
		}

		return score;
	}

	/**
	 * Validates the player's picks for the given week.
	 */
	public boolean validate( String[] errMsg )
	{
		int cnt = this.picks.size();

		if( cnt != getWeek().getGames().size() )
		{
			if( errMsg != null )
			{
				errMsg[0] = getPlayer()+" has wrong number of picks";
			}
			return false;
		}

		// sort the picks by ranking (ascending)

		List<Pick> sortedPicks = new ArrayList<Pick>(this.picks);
		Collections.sort(sortedPicks, IPick.COMPARE_RANKING);

		// verify that each pick's ranking is valid;
		// the list of rankings should be {1, 2, 3, ..., cnt}

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
				if( errMsg != null )
				{
					errMsg[0] = getPlayer() + " has illegal ranking for " + pick;
				}
				return false;
			}
			else if( ranking != (i + 1) )
			// if the ranking is not what we would expect, then it's too low
			// (which is certainly legal)
			{
				if( errMsg != null )
				{
					errMsg[0] = getPlayer() + " has invalid ranking for " + pick;
				}
				return false;
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