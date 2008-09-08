package org.lastbubble.shliktr;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * @version $Id$
 */
public abstract class ShliktrTestCase extends TestCase
{
	protected static BeanFactory beanFactory = createBeanFactory();

	//---------------------------------------------------------------------------
	// Setup methods
	//---------------------------------------------------------------------------

	private static BeanFactory createBeanFactory()
	{
		return new ClassPathXmlApplicationContext( new String[] {
				"dataConfig.xml", "applicationContext.xml"
			}
		);
	}

	protected static <T> T getBean( String name, Class<T> beanClass )
	{
		return (T) beanFactory.getBean(name, beanClass);
	}

}