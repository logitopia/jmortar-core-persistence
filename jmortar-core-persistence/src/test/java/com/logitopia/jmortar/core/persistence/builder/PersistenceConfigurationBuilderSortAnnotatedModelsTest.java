package com.logitopia.jmortar.core.persistence.builder;

import com.logitopia.jmortar.core.object.annotation.AnnotationSearcher;
import com.logitopia.jmortar.core.persistence.fixtures.AnnotatedNonParentModel;
import com.logitopia.jmortar.core.persistence.fixtures.ParentModel;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * A unit test of the <tt>sortAnnotatedModels</tt> method on the <tt>PersistenceConfigurationBuilder</tt>.
 */
public final class PersistenceConfigurationBuilderSortAnnotatedModelsTest
        extends AbstractUnitTest<PersistenceConfigurationBuilder> {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(PersistenceConfigurationBuilderSortAnnotatedModelsTest.class);

    /**
     * A list of model classes to test with.
     */
    private List<Class> testClassList;

    /**
     * Default Constructor.
     */
    public PersistenceConfigurationBuilderSortAnnotatedModelsTest() {
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
        testClassList = new ArrayList<>();
        testClassList.add(AnnotatedNonParentModel.class);
        testClassList.add(ParentModel.class);
        testClassList.add(AnnotatedNonParentModel.class);
        testClassList.add(ParentModel.class);

        setSubject(new PersistenceConfigurationBuilder(mock(AnnotationSearcher.class), mock(Map.class), 1));
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

        executePrivateMethod("sortAnnotatedModels",
                new Class[]{List.class},
                new Object[]{testClassList});

        assertEquals("Is the sorted list the correct size", 4, testClassList.size());

        // Check the first two entries
        assertEquals("Check the first entry", AnnotatedNonParentModel.class, testClassList.get(0));
        assertEquals("Check the second entry", AnnotatedNonParentModel.class, testClassList.get(1));

        // Check the second two entries
        assertEquals("Check the third entry", ParentModel.class, testClassList.get(2));
        assertEquals("Check the fourth entry", ParentModel.class, testClassList.get(3));
    }
}
