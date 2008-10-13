package org.lastbubble.shliktr.dao.hibernate;

import org.lastbubble.shliktr.IPick;
import org.lastbubble.shliktr.IPlayer;
import org.lastbubble.shliktr.ITeam;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.PickStats;
import org.lastbubble.shliktr.PoolResult;
import org.lastbubble.shliktr.dao.Game;
import org.lastbubble.shliktr.dao.Player;
import org.lastbubble.shliktr.dao.PoolEntry;
import org.lastbubble.shliktr.dao.PoolDao;
import org.lastbubble.shliktr.dao.Week;

import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * @version $Id: PoolDaoHibernate.java 69 2007-12-06 03:54:33Z eheaton $
 */
public final class PoolDaoHibernate implements PoolDao
{
	private SessionFactory sessionFactory;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public PoolDaoHibernate( SessionFactory sessionFactory )
	{
		this.sessionFactory = sessionFactory;
	}


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	/**
	 * @return	the Hibernate Session to use.
	 */
	private Session getSession()
	{
		return this.sessionFactory.getCurrentSession();
	}



	//-------------------------------------------------------------------------
	// Implements PoolService
	//-------------------------------------------------------------------------

	public Week findWeekById( Integer id )
	{
		return (Week) getSession().get(Week.class, id);
	}

	public Week findCurrentWeek()
	{
		int weekId = 1;

		try {
			java.sql.Connection conn = getSession().connection();
			java.sql.Statement stmt = conn.createStatement();
			java.sql.ResultSet rset = stmt.executeQuery(
				"select wg.week_id from week_game wg join game g "+
				"on wg.games_id = g.id "+
				"where datediff(curdate(), g.playedOn) <= 0 "+
				"order by wg.week_id limit 1");
			if( rset.next() )
			{
				weekId = rset.getInt(1);
			}

		} catch( Throwable t ) { }

		return findWeekById(weekId);
	}

	public Date findStartOfWeek( Integer id )
	{
		return (Date) getSession()
			.createCriteria(Week.class)
			.createAlias("games", "game")
			.add(Restrictions.idEq(id))
			.setProjection(Projections.min("game.playedOn"))
			.uniqueResult();
	}

	public List<Player> findAllPlayers()
	{
		Criteria crit = getSession().createCriteria(Player.class);

		return crit.list();
	}

	public Set<Player> findPlayersForWeek( IWeek week )
	{
		Set<Player> players = new HashSet<Player>();

		players.addAll(getSession().createQuery(
			"select picks.player from PoolEntry picks where picks.week = ?")
			.setEntity(0, week)
			.list());

		return players;
	}

	public Player findPlayerById( Integer id )
	{
		return (Player) getSession().get(Player.class, id);
	}

	public Player findPlayerByUsername( String username )
	{
		return (Player) getSession()
			.createCriteria(Player.class)
			.add(Restrictions.eq("username", username))
			.uniqueResult();
	}

	public List<PoolEntry> findEntriesForWeek( IWeek week )
	{
		return getSession()
			.createCriteria(PoolEntry.class)
			.createAlias("week", "week")
			.add(Restrictions.eq("week.id", week.getWeekNumber()))
			.list();
	}

	public List<PoolResult> findResultsForWeek( int week )
	{
		List<PoolResult> results = (List<PoolResult>) getSession()
			.getNamedQuery("entry.findResultsForWeek")
			.setInteger("week", week)
			.list();

		Collections.sort(results);

		return results;
	}

	public List<PoolResult> findAllResults()
	{
		return (List<PoolResult>) getSession()
			.getNamedQuery("entry.findAllResults")
			.list();
	}

	public PoolEntry findEntry( IWeek week, IPlayer player )
	{
		return (PoolEntry) getSession()
			.createCriteria(PoolEntry.class)
			.createAlias("week", "week")
			.createAlias("player", "player")
			.add(Restrictions.eq("week.id", week.getWeekNumber()))
			.add(Restrictions.eq("player.username", player.getUsername()))
			.uniqueResult();
	}

	public List<PickStats> findPickStatsForWeek( IWeek week )
	{
		Map<ITeam, PickStats> statsByTeam = new HashMap<ITeam, PickStats>();

		List<? extends IPick> picks = (List<? extends IPick>) getSession()
			.getNamedQuery("pick.findForWeek")
			.setParameter("week", week)
			.list();

		for( IPick pick : picks )
		{
			ITeam team = pick.getTeam();
			if( team == null ) continue;

			PickStats stats = statsByTeam.get(team);

			if( stats == null )
			{
				stats = new PickStats(team);
				statsByTeam.put(team, stats);
			}

			stats.addRanking(pick.getRanking());
		}

		List<PickStats> pickStats = new ArrayList<PickStats>(statsByTeam.values());

		Collections.sort(pickStats);

		return pickStats;
	}

	/** @see	PoolDao#findPlayerRankingsForTeam */
	public Map<IPlayer, Integer> findPlayerRankingsForTeam(
		int week, String team )
	{
		Map<IPlayer, Integer> playerRankings = new HashMap<IPlayer, Integer>();

		List<Object[]> resultSet = (List<Object[]>) getSession()
			.getNamedQuery("pick.findForTeam")
			.setInteger("week", week)
			.setParameter("team", team)
			.list();

		for( Object[] row : resultSet )
		{
			playerRankings.put((IPlayer) row[0], (Integer) row[1]);
		}

		return playerRankings;
	}

	public void refreshGamesForWeek( Week week )
	{
		Session session = getSession();

		for( Game game : week.getGames() )
		{
			session.refresh(game);
		}
	}

	public void persistWeek( Week week )
	{
		getSession().saveOrUpdate(week);
	}

	public void persistEntry( PoolEntry entry )
	{
		getSession().saveOrUpdate(entry);
	}
}
