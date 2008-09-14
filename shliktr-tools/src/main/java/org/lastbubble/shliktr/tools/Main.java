package org.lastbubble.shliktr.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version $Id$
 */
public final class Main
{
	private BeanFactory beanFactory;


	//-------------------------------------------------------------------------
	// Main
	//-------------------------------------------------------------------------

	public static void usage()
	{
		System.err.println("Main Version: $Rev$");
		System.err.println("Usage:");
		System.err.println("  Main dump <weekNumber> [<file>]");
		System.err.println("    or");
		System.err.println("  Main load <file>");
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
		this.beanFactory = new ClassPathXmlApplicationContext( new String[] {
				"dataConfig.xml", "applicationContext.xml", "tools-config.xml"
			}
		);
	}


	//-------------------------------------------------------------------------
	// Commands
	//-------------------------------------------------------------------------

	public void run( String[] args ) throws Exception
	{
		int argsLen = args.length;

		if( argsLen == 0 ) usage();

		String sCommand = args[0];

		String[] commandArgs = new String[argsLen - 1];
		if( argsLen > 0 )
			System.arraycopy(args, 1, commandArgs, 0, argsLen - 1);

		if( sCommand.equals("dump") )
		{
			dump(commandArgs);
		}
		else if( sCommand.equals("load") )
		{
			load(commandArgs);
		}
		else
		{
			usage();
		}
	}

	public void dump( String[] args ) throws Exception
	{
		if( args.length == 0 ) usage();

		int weekNumber = 0;
		try { weekNumber = Integer.parseInt(args[0]); }
		catch( NumberFormatException e ) { usage(); }

		Writer writer;
		if( args.length > 1 )
		{
			writer = new FileWriter(args[1]);
		}
		else
		{
			writer = new OutputStreamWriter(System.out);
		}

		PrintWriter printWriter = new PrintWriter(writer);

		PoolDumper dumper = (PoolDumper) this.beanFactory.getBean("dumper");

		dumper.dump(weekNumber, printWriter);

		printWriter.flush();
		printWriter.close();
	}

	public void load( String[] args ) throws Exception
	{
		if( args.length == 0 ) usage();

		String filename = args[0];

		BufferedReader reader = new BufferedReader( new FileReader(filename));

		PoolLoader loader = (PoolLoader) this.beanFactory.getBean("loader");

		loader.load(reader);
	}
}