package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IPoolEntry;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A player's picks for a specified week's games. Includes the player's
 * tiebreaker guess for that week.
 *
 * @version $Id$
 */
@Entity
@Table(name = "picks")
public class PoolEntry implements IPoolEntry, Comparable<IPoolEntry>
{
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Week week;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Player player;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinTable(
		name = "picks_pick",
		joinColumns = {@JoinColumn(name = "picks_id")},
		inverseJoinColumns = @JoinColumn(name = "pick_id")
	)
	private List<Pick> picks;

	private int tiebreaker;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	PoolEntry() { }

	PoolEntry( Week week, Player player, List<Pick> picks )
	{
		this.week = week;
		this.player = player;
		this.picks = picks;
	}

	public static PoolEntry createForPlayer( Week week, Player player )
	{
		PoolEntry entry = new PoolEntry();

		entry.setWeek(week);
		entry.setPlayer(player);

		List<Game> games = week.getGames();

		int cnt = games.size();

		List<Pick> pickList = new ArrayList<Pick>(cnt);

		for( int i = 0; i < cnt; i++ )
		{
			Pick pick = new Pick();
			pick.setGame(games.get(i));
			pickList.add(pick);
		}

		entry.setPicks(pickList);

		return entry;
	}


	//---------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------

	public Integer getId() { return this.id; }

	void setId( Integer n ) { this.id = n; }

	public Week getWeek() { return this.week; }

	void setWeek( Week w ) { this.week = w; }

	public Player getPlayer() { return this.player; }

	void setPlayer( Player p ) { this.player = p; }

	public List<? extends Pick> getPicks()
	{
		return Collections.unmodifiableList(this.picks);
	}

	void setPicks( List<Pick> l ) { this.picks = l; }

	public int size() { return this.picks.size(); }

	public Pick getPickAt( int i ) { return this.picks.get(i); }

	public int getTiebreaker() { return this.tiebreaker; }

	public void setTiebreaker( int n ) { this.tiebreaker = n; }

	/**
	 * Validates the player's picks for the given week.
	 */
	public boolean validate( String[] errMsg )
	{
		int cnt = size();

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

	/** Implements Comparable, so picks can be sorted by player. */
	public int compareTo( IPoolEntry entry )
	{
		return getPlayer().compareTo(entry.getPlayer());
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