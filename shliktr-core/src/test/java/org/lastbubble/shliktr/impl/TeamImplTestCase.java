package org.lastbubble.shliktr.impl;

import org.lastbubble.shliktr.ITeam;

import java.util.*;

import junit.framework.TestCase;

import static org.easymock.EasyMock.*;

/**
 * @version $Id$
 */
public class TeamImplTestCase extends TestCase
{
	private TeamImpl team;


	//---------------------------------------------------------------------------
	// Template methods
	//---------------------------------------------------------------------------

	protected TeamImpl newImpl() { return new TeamImpl(); }

	protected TeamImpl newImpl( String abbr ) { return new TeamImpl(abbr); }


	//---------------------------------------------------------------------------
	// Setup
	//---------------------------------------------------------------------------

	@Override
	protected void setUp()
	{
		team = newImpl("abc");
	}


	//---------------------------------------------------------------------------
	// Tests
	//---------------------------------------------------------------------------

	public void testCreate()
	{
		team = newImpl();
		assertNull(team.getAbbr());
		assertNull(team.getLocation());
		assertNull(team.getName());
	}

	public void testCreateWithAbbr()
	{
		String abbr = "def";
		assertEquals(abbr, newImpl(abbr).getAbbr());
	}

	public void testCreateWithAbbr_illegal()
	{
		assertCreateAbbrIllegal(null, "abbr cannot be null");
		assertCreateAbbrIllegal("", "abbr cannot be empty");
		assertCreateAbbrIllegal("   ", "cannot change abbr to empty string");
		assertCreateAbbrIllegal("abcd", "abbr must be 3 chars or less");
	}

	protected void assertCreateAbbrIllegal( String abbr, String reason )
	{
		try {
			team = newImpl(abbr);
			fail("create for "+abbr+" should throw exception: "+reason);
		} catch( Throwable t ) {
			assertTrue("create for "+abbr+" should throw IllegalArgumentException",
				(t instanceof IllegalArgumentException));
		}
	}

	public void testSetAbbr()
	{
		String abbr = "abc";

		team = newImpl();
		team.setAbbr(abbr);

		assertEquals(abbr, team.getAbbr());
	}

	public void testSetAbbr_illegal()
	{
		team = newImpl("abc");

		team.setAbbr("abc");

		assertSetAbbrIllegal(team, "def", "cannot change abbr");
		assertSetAbbrIllegal(team, null, "cannot change abbr to null");
		assertSetAbbrIllegal(team, "", "cannot change abbr to empty string");
		assertSetAbbrIllegal(team, "   ", "cannot change abbr to empty string");

		assertSetAbbrIllegal(newImpl(), "abcd", "abbr must be 3 chars or less");
	}

	protected void assertSetAbbrIllegal(
		TeamImpl team, String abbr, String reason )
	{
		try {
			team.setAbbr(abbr);
			fail("setAbbr("+abbr+") should throw exception: "+reason);
		} catch( Throwable t ) {
			assertTrue("setAbbr("+abbr+") should throw IllegalArgumentException",
				(t instanceof IllegalArgumentException));
		}
	}

	public void testSetLocation()
	{
		String location = "test";

		team = newImpl();
		team.setLocation(location);

		assertEquals(location, team.getLocation());
	}

	public void testSetName()
	{
		String name = "test";

		team = newImpl();
		team.setName(name);

		assertEquals(name, team.getName());
	}

	public void testCompareTo()
	{
		team = newImpl();

		assertTrue(team.compareTo(null) == 0);

		TeamImpl team2 = newImpl();

		assertTrue(team.compareTo(team2) == 0);
		assertTrue(team2.compareTo(team) == 0);

		team2.setAbbr("def");
		assertTrue(team.compareTo(team2) > 0);
		assertTrue(team2.compareTo(team) < 0);

		team.setAbbr("abc");
		assertTrue(team.compareTo(team2) < 0);
		assertTrue(team2.compareTo(team) > 0);
	}

	public void testEquals()
	{
		String abbr = "abc";
		TeamImpl team = newImpl(abbr);

		assertEquals(team, team);
		assertEquals(team, newImpl(abbr));
		assertFalse(team.equals(newImpl("def")));

		ITeam mock = createMock(ITeam.class);
		expect(mock.getAbbr()).andReturn(abbr);
		replay(mock);

		assertEquals(team, mock);

		assertFalse(team.equals(abbr));
	}

	public void testHashCode()
	{
		String abbr = "abc";

		Set<TeamImpl> teams = new HashSet<TeamImpl>();
		teams.add(newImpl(abbr));
		teams.add(newImpl(abbr));

		assertEquals(1, teams.size());
	}

	public void testToString()
	{
		String abbr = "abc";

		String s = newImpl(abbr).toString();
		assertTrue(s.indexOf("abbr="+abbr) > -1);
	}
}