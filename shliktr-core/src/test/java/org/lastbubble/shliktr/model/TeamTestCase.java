package org.lastbubble.shliktr.model;

import org.lastbubble.shliktr.impl.TeamImpl;

import org.lastbubble.shliktr.impl.TeamImplTestCase;

/**
 * @version $Id$
 */
public class TeamTestCase extends TeamImplTestCase
{
	@Override
	protected TeamImpl newImpl() { return new Team(); }

	@Override
	protected TeamImpl newImpl( String abbr ) { return new Team(abbr); }

}