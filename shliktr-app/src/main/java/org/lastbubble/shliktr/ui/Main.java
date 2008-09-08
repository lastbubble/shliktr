package org.lastbubble.shliktr.ui;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version $Id$
 */
public final class Main
{

	//-------------------------------------------------------------------------
	// Main
	//-------------------------------------------------------------------------

	public static void usage()
	{
		System.err.println("Main Version: $Rev$");
		System.err.println("Usage: Main [start]");
		System.exit(1);
	}

	public static void main( String[] args ) throws Exception
	{
		new Main().run(args);
	}


	//-------------------------------------------------------------------------
	// Constructor
	//-------------------------------------------------------------------------

	public Main()
	{
	}


	//-------------------------------------------------------------------------
	// Commands
	//-------------------------------------------------------------------------

	public void run( String[] args ) throws Exception
	{
		if( args.length == 0 ) usage();

		String sCommand = args[0];

		if( sCommand.equals("start") )
		{
			start();
		}
		else
		{
			usage();
		}
	}

	public void start() throws Exception
	{
		BeanFactory beanFactory =
			new ClassPathXmlApplicationContext( new String[] {
				"dataConfig.xml", "applicationContext.xml", "pool-config.xml" });

		final Pool pool = (Pool) beanFactory.getBean("pool", Pool.class);

		SwingUtilities.invokeLater( new Runnable() {
			public void run()
			{
				pool.start();
			}
		});
	}

}	// End of Main
