package com.logitopia.jmortar.core.persistence.builder;

import com.logitopia.jmortar.core.persistence.fixtures.AnnotatedNonParentModel;
import com.logitopia.jmortar.core.persistence.fixtures.ParentModel;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import com.logitopia.jmortar.core.test.exception.PrivateTestMethodException;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * A unit test of <tt>PersistentComparator</tt>.
 */
public final class PersistentComparatorTest
        extends AbstractUnitTest<PersistentComparator> {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(PersistentComparatorTest.class);

    /**
     * Default Constructor.
     */
    public PersistentComparatorTest() {
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
        setSubject(new PersistentComparator());
    }

    /**
     * {@inheritDoc}
     */
    @After
    public void tearDown() {
    }

    /**
     * Test that, given basic positive inputs, we get basic positive outputs.
     */
    @Test
    public void testIsModelParentBasicSuccess() {
        LOG.info("Test basic success");

        Object result = null;
        try {
            result = executePrivateMethod("isModelParent",
                    new Class[]{Class.class},
                    new Object[]{ParentModel.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run isModelParent method.");
        }

        assertNotNull("Is the result null", result);
        assertTrue("Is the result the expected type", result instanceof Boolean);

        Boolean resultFlag = (Boolean) result;

        assertTrue("Is the value as expected (true)", resultFlag);
    }

    /**
     * Test that, given basic negative inputs, we get basic positive outputs.
     */
    @Test
    public void testIsModelParentSuccessWithAnnotatedNoParent() {
        LOG.info("Test success with Annotated model that is not a parent");

        Object result = null;
        try {
            result = executePrivateMethod("isModelParent",
                    new Class[]{Class.class},
                    new Object[]{AnnotatedNonParentModel.class});
        } catch (PrivateTestMethodException e) {
            fail("Unable to run isModelParent method.");
        }

        assertNotNull("Is the result null", result);
        assertTrue("Is the result the expected type", result instanceof Boolean);

        Boolean resultFlag = (Boolean) result;

        assertFalse("Is the value as expected (false)", resultFlag);
    }

    /**
     * Test that, given positive input to <tt>compare</tt>, we get successful positive output.
     */
    @Test
    public void testCompareBasicSuccess() {
        LOG.info("Test compare basic success");

        int result = getSubject().compare(ParentModel.class,
                AnnotatedNonParentModel.class);

        assertEquals("Is the result expected", 1, result);
    }

    /**
     * Test that, given positive input to <tt>compare</tt>, we get successful positive output.
     */
    @Test
    public void testCompareNonParentToParent() {
        LOG.info("Test compare parent model to non parent model");

        int result = getSubject().compare(AnnotatedNonParentModel.class,
                ParentModel.class);

        assertEquals("Is the result expected", -1, result);
    }

    /**
     * Test that, given positive input to <tt>compare</tt>, we get successful positive output.
     */
    @Test
    public void testCompareParentToParent() {
        LOG.info("Test compare parent model to parent model");

        int result = getSubject().compare(ParentModel.class,
                ParentModel.class);

        assertEquals("Is the result expected", 0, result);
    }

    /**
     * Test that, given positive input to <tt>compare</tt>, we get successful positive output.
     */
    @Test
    public void testCompareNonParentToNonParent() {
        LOG.info("Test compare non parent model to non parent model");

        int result = getSubject().compare(AnnotatedNonParentModel.class,
                AnnotatedNonParentModel.class);

        assertEquals("Is the result expected", 0, result);
    }
}
