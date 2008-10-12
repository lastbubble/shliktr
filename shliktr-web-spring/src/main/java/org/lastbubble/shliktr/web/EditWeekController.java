package org.lastbubble.shliktr.web;

import org.lastbubble.shliktr.IPoolEntry;
import org.lastbubble.shliktr.IWeek;
import org.lastbubble.shliktr.service.PoolService;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * @version $Id$
 */
public class EditWeekController extends SimpleFormController
{
	private PoolService poolService;


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public EditWeekController()
	{
		setCommandName("week");
		setCommandClass(IWeek.class);
		setFormView("editWeek");
		setSuccessView("redirect:/app/viewWeek");
	}



	//-------------------------------------------------------------------------
	// Properties
	//-------------------------------------------------------------------------

	public void setPoolService( PoolService ps ) { this.poolService = ps; }


	//-------------------------------------------------------------------------
	// SimpleFormController methods
	//-------------------------------------------------------------------------

	@Override
	protected Map referenceData(
		HttpServletRequest request, Object command, Errors errors )
	throws Exception
	{
		Map map = new HashMap();
		map.put("player", WebUtils.getPlayerFromRequest(request));
		return map;
	}

	@Override
	protected Object formBackingObject( HttpServletRequest request )
	throws Exception
	{
		String s = request.getParameter("weekId");

		int weekId = 1;
		if( s != null && s.length() > 0 )
		{
			try { weekId = Integer.parseInt(s); }
			catch( NumberFormatException e ) { }
		}

		IWeek week = this.poolService.findWeekById(weekId);

		if( week == null )
		{
			throw new IllegalArgumentException("Invalid week id: "+s);
		}

		return week;
	}

	@Override
	protected ModelAndView onSubmit( Object command ) throws Exception
	{
		IWeek week = (IWeek) command;

		this.poolService.saveWeek(week);

		List<? extends IPoolEntry> entries = this.poolService
			.findEntriesForWeek(week);
		for( IPoolEntry entry : entries )
		{
			entry.updateScore();
			this.poolService.saveEntry(entry);
		}

		ModelAndView mv = new ModelAndView(getSuccessView());
		mv.addObject("weekId", week.getWeekNumber());
		return mv;
	}
}
