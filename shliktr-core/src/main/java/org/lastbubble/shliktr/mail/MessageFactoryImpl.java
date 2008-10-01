package org.lastbubble.shliktr.mail;

import org.lastbubble.shliktr.IPoolEntry;

import java.io.StringWriter;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * @version $Id$
 */
public class MessageFactoryImpl implements IMessageFactory
{
	//---------------------------------------------------------------------------
	// Implements IMessageFactory
	//---------------------------------------------------------------------------

	/** @see	IMessageFactory#messageForEntry */
	public String messageForEntry( IPoolEntry entry ) throws Exception
	{
		Properties props = new Properties();
		props.setProperty("resource.loader", "class");
		props.setProperty("class.resource.loader.description",
			"Velocity Classpath Resource Loader");
		props.setProperty("class.resource.loader.class",
		 	"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		VelocityEngine engine = new VelocityEngine(props);
		engine.init();

		Template template = engine
			.getTemplate("org/lastbubble/shliktr/mail/entry.vm");

		VelocityContext context = new VelocityContext();
		context.put("entry", entry);

		StringWriter writer = new StringWriter();
		template.merge(context, writer);

		return writer.toString();
	}
}