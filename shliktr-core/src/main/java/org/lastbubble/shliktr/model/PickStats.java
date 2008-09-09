package org.lastbubble.shliktr.model;

import java.util.*;

/**
 * @version $Id$
 */
public class PickStats implements Comparable<PickStats>
{
	private Team team;

	private List<Integer> rankings;


	//---------------------------------------------------------------------------
	// Constructor
	//---------------------------------------------------------------------------

	public PickStats( Team team )
	{
		this.team = team;
		this.rankings = new ArrayList<Integer>();
	}


	//---------------------------------------------------------------------------
	// Methods
	//---------------------------------------------------------------------------

	public Team getTeam() { return this.team; }

	public List<Integer> getRankings()
	{
		List<Integer> rankings = new ArrayList<Integer>(this.rankings);
		Collections.sort(rankings, Collections.reverseOrder());
		return rankings;
	}

	public int getTotal()
	{
		int total = 0;

		for( Integer ranking : this.rankings )
		{
			total += ranking;
		}

		return total;
	}

	public void addRanking( int ranking ) { this.rankings.add(ranking); }


	//---------------------------------------------------------------------------
	// Implements Comparable<PickStats>
	//---------------------------------------------------------------------------

	public int compareTo( PickStats stats )
	{
		int delta = stats.rankings.size() - this.rankings.size();

		if( delta == 0 )
		{
			delta = stats.getTotal() - this.getTotal();
		}

		return delta;
	}

}
