package org.lastbubble.shliktr.dao;

import org.lastbubble.shliktr.ShliktrTestCase;

import javax.sql.DataSource;

import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;

/**
 * @version $Id$
 */
public abstract class DaoTestCase extends ShliktrTestCase
{
	protected static IDatabaseTester databaseTester = createDatabaseTester();

	protected static PoolDao poolDao = getBean("poolDao", PoolDao.class);

	private static PlatformTransactionManager txManager = getBean("txManager",
		PlatformTransactionManager.class);

	private TransactionStatus txStatus;


	//---------------------------------------------------------------------------
	// Setup methods
	//---------------------------------------------------------------------------

	protected static IDatabaseTester createDatabaseTester()
	{
		IDatabaseTester databaseTester = null;

		DataSource dataSource = getBean("dataSource", DataSource.class);

		try {
			IDatabaseConnection conn =
				new DatabaseDataSourceConnection(dataSource);

			conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
				new HsqldbDataTypeFactory());

			// turn off "data type not recognized" warnings
			conn.getConfig().setFeature(DatabaseConfig.FEATURE_DATATYPE_WARNING, false);

			databaseTester = new DefaultDatabaseTester(conn);
			databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);

		} catch( Exception e ) {
			throw new RuntimeException("createDatabaseTester failed: " + e, e);
		}

		return databaseTester;
	}

	protected static void createDatabase( IDataSet dataSet )
	{
		LocalSessionFactoryBean sessionFactoryBean = getBean("&sessionFactory",
			LocalSessionFactoryBean.class);

		sessionFactoryBean.dropDatabaseSchema();

		sessionFactoryBean.createDatabaseSchema();

		if( dataSet == null ) return;

		try {
			if( databaseTester.getDataSet() != null )
			{
				databaseTester.onTearDown();
			}

			databaseTester.setDataSet(dataSet);

			databaseTester.onSetup();

		} catch( Exception e ) {
			throw new RuntimeException("createDatabase(" + dataSet + ") failed: "
			 + e, e);
		}
	}

	protected static IDataSet createDataSetResource( String resource )
	{
		IDataSet dataSet = null;

		try {
			dataSet = new FlatXmlDataSet(DaoTestCase.class.getClassLoader()
				.getResourceAsStream(resource)
			);

		} catch( Throwable t ) {
			throw new RuntimeException("createDataSetResource(" + resource
			 + ") failed: " + t, t);
		}

		return dataSet;
	}


	//---------------------------------------------------------------------------
	// Transaction methods
	//---------------------------------------------------------------------------

	protected void startTransaction( boolean readOnly )
	{
		DefaultTransactionDefinition txDefn =
			new DefaultTransactionDefinition();

		txDefn.setReadOnly(readOnly);

		txStatus = txManager.getTransaction(txDefn);
	}

	protected void commitTransaction()
	{
		txManager.commit(txStatus);
	}

	protected void rollbackTransaction()
	{
		txManager.rollback(txStatus);
	}


	//---------------------------------------------------------------------------
	// JUnit methods
	//---------------------------------------------------------------------------

	@Override
	protected final void setUp()
	{
		startTransaction(false);

		onSetUp();
	}

	/** Template method */
	protected void onSetUp() { }

	@Override
	protected final void tearDown()
	{
		rollbackTransaction();
	}

}