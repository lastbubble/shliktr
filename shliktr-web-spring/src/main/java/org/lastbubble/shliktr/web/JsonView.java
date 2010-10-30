package org.lastbubble.shliktr.web;

import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

/**
 * @version $Id$
 */
public class JsonView implements View
{
	static final String CONTENT_TYPE = "application/json";

	/** @see	View#getContentType */
	public String getContentType() { return CONTENT_TYPE; }

	/** @see	View#render */
	public void render( Map model, HttpServletRequest request, HttpServletResponse response )
	throws Exception
	{
		JSONObject json = new JSONObject();
		for( Object key : model.keySet() )
		{
			Object value = model.get(key);
			if( value instanceof JSON )
			{
				json.element(key.toString(), value);
			}
		}

		response.setContentType(CONTENT_TYPE);
		response.getWriter().write(json.toString());
	}
}
