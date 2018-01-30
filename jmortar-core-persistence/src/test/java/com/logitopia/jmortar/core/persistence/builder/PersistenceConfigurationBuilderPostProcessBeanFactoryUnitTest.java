package com.logitopia.jmortar.core.persistence.builder;

import com.logitopia.jmortar.core.test.AbstractUnitTest;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static junit.framework.TestCase.assertNotNull;

/**
 * Created by stephen on 23/11/2017.
 */
public final class PersistenceConfigurationBuilderPostProcessBeanFactoryUnitTest
        extends AbstractUnitTest<PersistenceConfigurationBuilder> {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(PersistenceConfigurationBuilderPostProcessBeanFactoryUnitTest.class);

    /**
     * Application Context.
     */
    private final ApplicationContext context
            = new ClassPathXmlApplicationContext("builder-app.xml");

    /**
     * Default Constructor.
     */
    public PersistenceConfigurationBuilderPostProcessBeanFactoryUnitTest() {
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
    }

    /**
     * {@inheritDoc}
     */
    @After
    public void tearDown() {
    }

    /**
     * Test that, given basic positive input, we get a basic successful outcome.
     */
    @Test
    public void testBasicSuccess() {
        LOG.info("Test basic success");

        Object result = context.getBean("basicModel");

        assertNotNull("Is the result null", result);
    }
}
