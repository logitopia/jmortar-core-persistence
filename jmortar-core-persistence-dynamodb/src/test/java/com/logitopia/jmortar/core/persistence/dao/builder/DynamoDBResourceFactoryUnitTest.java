package com.logitopia.jmortar.core.persistence.dao.builder;

import com.logitopia.jmortar.core.persistence.dao.fixtures.MockDynamoDBModel;
import com.logitopia.jmortar.core.persistence.dao.fixtures.MockDynamoDBWithParams;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import com.logitopia.jmortar.core.test.exception.PrivateTestMethodException;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * The <tt>DynamoDBResourceFactoryUnitTest</tt> is a unit testing of the <tt>DynamoDBResourceFactory</tt> class.
 */
public class DynamoDBResourceFactoryUnitTest
        extends AbstractUnitTest<DynamoDBResourceFactory> {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(DynamoDBResourceFactoryUnitTest.class);

    /**
     * Default Constructor.
     */
    public DynamoDBResourceFactoryUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        setSubject(new DynamoDBResourceFactory());
    }

    @After
    public void tearDown() {
    }

    /**
     * Test that, given positive input, we get a positive output.
     */
    @Test
    public void testGetCredentialsProviderBeanNameNoCredentialsProvider() {
        LOG.info("Test getSessionFactoryBeanName with no credentials provider in model");

        Object resultObj = null;
        try {
            resultObj = executePrivateMethod("getCredentialsProviderBeanName",
                    new Class[]{Class.class},
                    new Object[]{MockDynamoDBModel.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run getCredentialsProviderBeanName method.");
        }

        assertNotNull("Is the result null", resultObj);
        assertTrue("Is the result type a String", resultObj instanceof String);

        String result = (String) resultObj;
        assertEquals("Does the result match expected", "credProvider", result);
    }

    /**
     * Test that, given that a Credentials Provider is specified, the factory finds it and returns is.
     */
    @Test
    public void testGetCredentialsProviderBeanNameWithCredentialsProvider() {
        LOG.info("Test getCredentialsProviderBeanName with credentials provider in model");

        Object resultObj = null;
        try {
            resultObj = executePrivateMethod("getCredentialsProviderBeanName",
                    new Class[]{Class.class},
                    new Object[]{MockDynamoDBWithParams.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run getCredentialsProviderBeanName method.");
        }

        assertNotNull("Is the result null", resultObj);
        assertTrue("Is the result type a String", resultObj instanceof String);

        String result = (String) resultObj;
        assertEquals("Does the result match expected", "testCredentialsProvider", result);
    }

    /**
     * Test that, given positive regions input, we get a positive output.
     */
    @Test
    public void testGetRegionsProviderBeanNameNoCredentialsProvider() {
        LOG.info("Test getRegionsBeanName with no credentials provider in model");

        Object resultObj = null;
        try {
            resultObj = executePrivateMethod("getRegionsBeanName",
                    new Class[]{Class.class},
                    new Object[]{MockDynamoDBModel.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run getRegionsBeanName method.");
        }

        assertNotNull("Is the result null", resultObj);
        assertTrue("Is the result type a String", resultObj instanceof String);

        String result = (String) resultObj;
        assertEquals("Does the result match expected", "awsRegion", result);
    }

    /**
     * Test that, given that regions are specified, the factory finds it and returns is.
     */
    @Test
    public void testGetRegionsBeanNameWithRegions() {
        LOG.info("Test getRegionsBeanName with credentials provider in model");

        Object resultObj = null;
        try {
            resultObj = executePrivateMethod("getCredentialsProviderBeanName",
                    new Class[]{Class.class},
                    new Object[]{MockDynamoDBWithParams.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run getCredentialsProviderBeanName method.");
        }

        assertNotNull("Is the result null", resultObj);
        assertTrue("Is the result type a String", resultObj instanceof String);

        String result = (String) resultObj;
        assertEquals("Does the result match expected", "testCredentialsProvider", result);
    }

    /**
     * Test that, given positive regions input, we get a positive output.
     */
    @Test
    public void testGetNoOfThreadsNoThreads() {
        LOG.info("Test getNoOfThreads with no number of threads specified in model");

        Object resultObj = null;
        try {
            resultObj = executePrivateMethod("getNoOfThreads",
                    new Class[]{Class.class},
                    new Object[]{MockDynamoDBModel.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run the getNoOfThreads method.");
        }

        assertNotNull("Is the result null", resultObj);
        assertTrue("Is the result type an integer", resultObj instanceof Integer);

        int result = (int) resultObj;
        assertEquals("Does the result match expected", 1, result);
    }

    /**
     * Test that, given that No of Threads are specified, the factory finds it and returns is.
     */
    @Test
    public void testGetNoOfThreadsWithNoOfThreads() {
        LOG.info("Test getRegionsBeanName with credentials provider in model");

        Object resultObj = null;
        try {
            resultObj = executePrivateMethod("getNoOfThreads",
                    new Class[]{Class.class},
                    new Object[]{MockDynamoDBWithParams.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run the getNoOfThreads method.");
        }

        assertNotNull("Is the result null", resultObj);
        assertTrue("Is the result type a String", resultObj instanceof Integer);

        int result = (int) resultObj;
        assertEquals("Does the result match expected", 3, result);
    }
}
