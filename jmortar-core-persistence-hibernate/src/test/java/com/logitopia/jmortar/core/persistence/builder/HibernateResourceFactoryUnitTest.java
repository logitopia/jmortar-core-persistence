package com.logitopia.jmortar.core.persistence.builder;

import com.logitopia.jmortar.core.persistence.builder.exception.MalformedPersistentModelException;
import com.logitopia.jmortar.core.persistence.dao.builder.HibernateResourceFactory;
import com.logitopia.jmortar.core.persistence.dao.model.HibernateResource;
import com.logitopia.jmortar.core.persistence.fixtures.MockPersistentModel;
import com.logitopia.jmortar.core.persistence.fixtures.MockPersistentModelSessionFactoryBean;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import com.logitopia.jmortar.core.test.exception.PrivateTestMethodException;
import org.hibernate.SessionFactory;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * The <tt>HibernateResourceFactoryUnitTest</tt> is a unit testing of the <tt>HibernateResourceFactory</tt> class.
 */
public class HibernateResourceFactoryUnitTest
        extends AbstractUnitTest<HibernateResourceFactory> {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(HibernateResourceFactoryUnitTest.class);

    /**
     * Application Context.
     */
    private final ApplicationContext testContext
            = new ClassPathXmlApplicationContext("fixtures/sessionFactoryTestContext.xml");

    /**
     * The resource factory to test public methods with.
     */
    private final HibernateResourceFactory RESOURCE_FACTORY
            = (HibernateResourceFactory) testContext.getBean("hibernateResourceFactory");

    /**
     * Default Constructor.
     */
    public HibernateResourceFactoryUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        setSubject(new HibernateResourceFactory());
    }

    @After
    public void tearDown() {
    }

    /**
     * Test that, given positive input, we get a positive output.
     */
    @Test
    public void testGetSessionFactoryBeanNameNoSessionFactory() {
        LOG.info("Test getSessionFactoryBeanName with no Session Factory in model");

        Object resultObj = null;
        try {
            resultObj = executePrivateMethod("getSessionFactoryBeanName",
                    new Class[]{Class.class},
                    new Object[]{MockPersistentModel.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run getSessionFactoryBeanName method.");
        }

        assertNotNull("Is the result null", resultObj);
        assertTrue("Is the result type a String", resultObj instanceof String);

        String result = (String) resultObj;
        assertEquals("Does the result match expected", "sessionFactory", result);
    }

    /**
     * Test that, given that a session factory is specified, the factory finds and returns it.
     */
    @Test
    public void testGetSessionFactoryBeanNameWithSessionFactory() {
        LOG.info("Test getSessionFactoryBeanName with a Session Factory in model");

        Object resultObj = null;
        try {
            resultObj = executePrivateMethod("getSessionFactoryBeanName",
                    new Class[]{Class.class},
                    new Object[]{MockPersistentModelSessionFactoryBean.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run getSessionFactoryBeanName method.");
        }

        assertNotNull("Is the result null", resultObj);
        assertTrue("Is the result type a String", resultObj instanceof String);

        String result = (String) resultObj;
        assertEquals("Does the result match expected", "testSessionFactory", result);
    }

    /**
     * Test that, given the basic Spring context, the resource factory functions as expected.
     */
    @Test
    public void testBasicSpringContextSuccess() {
        LOG.info("Test basic spring context success");

        try {
            HibernateResource result = RESOURCE_FACTORY.build(MockPersistentModel.class);

            assertNotNull("Is the result null", result);
            assertNotNull("Is the session factory in the resource null", result.getSessionFactory());
        } catch (MalformedPersistentModelException e) {
            fail("Unable to process the given model");
        }
    }

    /**
     * Test that, given the basic Spring context, the resource factory functions as expected.
     */
    @Test
    public void testSpringContextSessionFactorySpecifiedSuccess() {
        LOG.info("Test spring context with session factory specified success");

        try {
            HibernateResource result = RESOURCE_FACTORY.build(MockPersistentModelSessionFactoryBean.class);

            assertNotNull("Is the result null", result);

            SessionFactory sessionFactory = result.getSessionFactory();
            assertNotNull("Is the session factory in the resource null", sessionFactory);
        } catch (MalformedPersistentModelException e) {
            fail("Unable to process the given model");
        }
    }
}
