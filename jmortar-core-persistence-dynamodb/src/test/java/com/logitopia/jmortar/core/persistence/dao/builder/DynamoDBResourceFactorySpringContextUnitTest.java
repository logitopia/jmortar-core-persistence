package com.logitopia.jmortar.core.persistence.dao.builder;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.logitopia.jmortar.core.persistence.builder.exception.MalformedPersistentModelException;
import com.logitopia.jmortar.core.persistence.dao.fixtures.MockDynamoDBModel;
import com.logitopia.jmortar.core.persistence.dao.fixtures.MockDynamoDBWithParams;
import com.logitopia.jmortar.core.persistence.dao.model.DynamoDBResource;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import org.apache.http.client.CredentialsProvider;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * The <tt>DynamoDBResourceFactorySpringContextUnitTest</tt> is unit testing of the <tt>DynamoDBResourceFactory</tt>
 * class.
 */
public class DynamoDBResourceFactorySpringContextUnitTest
        extends AbstractUnitTest<DynamoDBResourceFactory> {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(DynamoDBResourceFactorySpringContextUnitTest.class);

    /**
     * Application Context.
     */
    private final ApplicationContext testContext
            = new ClassPathXmlApplicationContext("fixtures/dynamoResourceTestContext.xml");

    /**
     * The resource factory to test public methods with.
     */
    private final DynamoDBResourceFactory RESOURCE_FACTORY
            = (DynamoDBResourceFactory) testContext.getBean("dynamoDBResourceFactory");

    /**
     * Default Constructor.
     */
    public DynamoDBResourceFactorySpringContextUnitTest() {
    }

    /**
     * {@inheritDoc}
     */
    @BeforeClass
    public static void setUpClass() {
    }

    /**
     * {@inheritDoc}
     */
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * {@inheritDoc}
     */
    @Before
    public void setUp() {
        setSubject(new DynamoDBResourceFactory());
    }

    /**
     * {@inheritDoc}
     */
    @After
    public void tearDown() {
    }

    // Spring Context Testing - Test that the handler works to get the bean from the Spring Context.

    /**
     * Test that, given the basic Spring context, the resource factory functions as expected.
     */
    @Test
    public void testBasicSpringContextSuccess() {
        LOG.info("Test basic spring context success");

        try {
            DynamoDBResource result = RESOURCE_FACTORY.build(MockDynamoDBModel.class);
            assertNotNull("Is the result null", result);
            assertNotNull("Is the credentials provider in the resource null", result.getCredentialsProvider());

            assertNotNull("Is the region in the resource null", result.getRegions());
            assertEquals("Does the regions match as expected", Regions.EU_WEST_1, result.getRegions());
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
            DynamoDBResource result = RESOURCE_FACTORY.build(MockDynamoDBWithParams.class);

            assertNotNull("Is the result null", result);
            AWSCredentialsProvider credProvider = result.getCredentialsProvider();
            assertNotNull("Is the credentials provider in the resource null", credProvider);

            assertNotNull("Is the region in the resource null", result.getRegions());
            assertEquals("Does the regions match as expected", Regions.EU_CENTRAL_1, result.getRegions());
        } catch (MalformedPersistentModelException e) {
            fail("Unable to process the given model");
        }
    }

}
