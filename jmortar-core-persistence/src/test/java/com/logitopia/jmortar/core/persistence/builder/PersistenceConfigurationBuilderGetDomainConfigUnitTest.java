package com.logitopia.jmortar.core.persistence.builder;

import com.logitopia.jmortar.core.object.annotation.AnnotationSearcher;
import com.logitopia.jmortar.core.persistence.builder.exception.MalformedPersistentModelException;
import com.logitopia.jmortar.core.persistence.builder.model.DomainPersistenceBuilderConfig;
import com.logitopia.jmortar.core.persistence.fixtures.builder.BasicModel;
import com.logitopia.jmortar.core.persistence.fixtures.builder.NonPersistentModel;
import com.logitopia.jmortar.core.persistence.fixtures.builder.annotation.InventedNonPersistent;
import com.logitopia.jmortar.core.persistence.fixtures.builder.annotation.InventedPersistent;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.mock;

/**
 * A unit test of the <tt>getDomainConfig</tt> private method on the <tt>PersistenceConfigurationBuilder</tt>.
 */
public class PersistenceConfigurationBuilderGetDomainConfigUnitTest
        extends AbstractUnitTest<PersistenceConfigurationBuilder> {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(PersistenceConfigurationBuilderGetDomainConfigUnitTest.class);

    /**
     * The domain configuration mapping to test with.
     */
    private Map<Class, DomainPersistenceBuilderConfig> domainConfig;

    /**
     * Mock configuration to test with.
     */
    private DomainPersistenceBuilderConfig mockConfig;

    /**
     * Default Constructor.
     */
    public PersistenceConfigurationBuilderGetDomainConfigUnitTest() {
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
        /* Setup the Domain Configuration Map */
        mockConfig = mock(DomainPersistenceBuilderConfig.class);
        domainConfig = new HashMap<>();

        domainConfig.put(InventedPersistent.class, mockConfig);
        domainConfig.put(InventedNonPersistent.class, mockConfig);

        setSubject(new PersistenceConfigurationBuilder(mock(AnnotationSearcher.class),
                domainConfig, 1));
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

        Object result = executePrivateMethod("getDomainConfig",
                new Class[]{Class.class},
                new Object[]{BasicModel.class});

        assertNotNull("Has a result been found?", result);
        assertTrue("Is the result the correct type?", result instanceof DomainPersistenceBuilderConfig);
        assertEquals("Is the result instance as expected?", mockConfig, result);
    }

    /**
     * Test that, if we pass in a model that is not an instance of a persistent, but in the map, it should still fail.
     */
    @Test
    public void testNonPersistentModel() {
        LOG.info("Test that a non persistent model throws an exception");

        try {
            Method privateMethod = this.getSubject().getClass().getDeclaredMethod("getDomainConfig", Class.class);
            privateMethod.setAccessible(true);
            privateMethod.invoke(this.getSubject(), NonPersistentModel.class);
            fail("An exception should have been thrown.");
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            assertTrue("Is the exception of the correct type", cause instanceof MalformedPersistentModelException);
            assertEquals("Does the exception contain the expected message",
                    "The model [NonPersistentModel] did not have a persistent annotation.",
                    cause.getMessage());
        }
    }
}
