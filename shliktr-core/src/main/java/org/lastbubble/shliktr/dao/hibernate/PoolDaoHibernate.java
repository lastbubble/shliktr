package org.lastbubble.shliktr.dao.hibernate;

import org.lastbubble.shliktr.dao.PoolDao;
import org.lastbubble.shliktr.model.Game;
import org.lastbubble.shliktr.model.Pick;
import org.lastbubble.shliktr.model.Picks;
import org.lastbubble.shliktr.model.PickStats;
import org.lastbubble.shliktr.model.Player;
import org.lastbubble.shliktr.model.Team;
import org.lastbubble.shliktr.model.Week;

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

	public Set<Player> findPlayersForWeek( Week week )
	{
		Set<Player> players = new HashSet<Player>();

		players.addAll(getSession().createQuery(
			"select picks.player from Picks picks where picks.week = ?")
			.setEntity(0, week)
			.list());

		return players;
	}

	public Player findPlayerById( Integer id )
	{
		return (Player) getSession().get(Player.class, id);
	}

	public Player findPlayerByName( String name )
	{
		Criteria crit = getSession().createCriteria(Player.class);

		crit.add(Restrictions.eq("name", name));

		return (Player) crit.uniqueResult();
	}

	public List<Picks> findPicksForWeek( Week week )
	{
		Criteria crit = getSession().createCriteria(Picks.class);

		crit.add(Restrictions.eq("week", week));

		return crit.list();
	}

	public Picks findPicksForPlayer( Week week, Player player )
	{
		Criteria crit = getSession().createCriteria(Picks.class);

		crit.add(Restrictions.eq("week", week));
		crit.add(Restrictions.eq("player", player));

		return (Picks) crit.uniqueResult();
	}

	public List<PickStats> findPickStatsForWeek( Week week )
	{
		List<Pick> picks = (List<Pick>) getSession()
			.createCriteria(Pick.class)
			.createAlias("game", "game")
			.add(Restrictions.eq("game.week", week))
			.list();

		Map<Team, PickStats> statsByTeam = new HashMap<Team, PickStats>();

		for( Pick pick : picks )
		{
			Team team = null;

			if( pick.getWinner() != null )
			{
				switch( pick.getWinner() )
				{
					case HOME: team = pick.getGame().getHomeTeam(); break;
					case AWAY: team = pick.getGame().getAwayTeam(); break;
				}
			}

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

	public void refreshGamesForWeek( Week week )
	{
		Session session = getSession();

		for( Game game : week.getGames() )
		{
			session.refresh(game);
		}
	}

	public void makePersistentWeek( Week week )
	{
		getSession().saveOrUpdate(week);
	}

	public void makePersistentPicks( Picks picks )
	{
		getSession().saveOrUpdate(picks);
	}

/*
	public Class<T> getPersistentClass() { return m_persistentClass; }

	protected List<T> findByCriteria( Criterion... criterion )
	{
		Criteria crit = getSession().createCriteria(getPersistentClass());

		for( Criterion c : criterion )
		{
			crit.add(c);
		}

		return crit.list();
	}


	//-------------------------------------------------------------------------
	// GenericDAO methods
	//-------------------------------------------------------------------------

	public T findById( ID id, boolean lock )
	{
		T entity;

		if( lock )
		{
			entity = (T) getSession().get(getPersistentClass(), id,
				LockMode.UPGRADE);
		}
		else
		{
			entity = (T) getSession().get(getPersistentClass(), id);
		}

		return entity;
	}

	public List<T> findAll()
	{
		return findByCriteria();
	}

	public List<T> findByExample( T example, String... excludeProperty )
	{
		Criteria crit = getSession().createCriteria(getPersistentClass());

		Example ex = Example.create(example);
		for( String exclude : excludeProperty )
		{
			ex.excludeProperty(exclude);
		}
		crit.add(ex);

		return crit.list();
	}

	public T makePersistent( T entity )
	{
		getSession().saveOrUpdate(entity);
		return entity;
	}

	public void makeTransient( T entity )
	{
		getSession().delete(entity);
	}

	public void flush()
	{
		getSession().flush();
	}

	public void clear()
	{
		getSession().clear();
	}
*/

}	// End of PoolDaoHibernate
