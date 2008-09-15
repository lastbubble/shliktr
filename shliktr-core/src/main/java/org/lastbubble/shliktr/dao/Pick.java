package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.ITeam;
import org.lastbubble.shliktr.PickBase;
import org.lastbubble.shliktr.Winner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A player's pick for an NFL game. Stores the game itself, as well as the
 * winner predicted by the player and the player's ranking for the game.
 *
 * @version $Id$
 */
@Entity
@Table(name = "pick")
public class Pick extends PickBase
{
	private Integer id;

	private Game game;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	Pick() { }


	//-------------------------------------------------------------------------
	// Methods
	//-------------------------------------------------------------------------

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() { return this.id; }
	void setId( Integer n ) { this.id = n; }

	@ManyToOne
	@JoinColumn(nullable = false)
	public Game getGame() { return this.game; }
	void setGame( Game g ) { this.game = g; }

	@Enumerated(EnumType.STRING)
	@Column(length = 4)
	public Winner getWinner() { return super.getWinner(); }

	@Transient
	public ITeam getTeam() { return super.getTeam(); }

	@Column
	public int getRanking() { return super.getRanking(); }
}