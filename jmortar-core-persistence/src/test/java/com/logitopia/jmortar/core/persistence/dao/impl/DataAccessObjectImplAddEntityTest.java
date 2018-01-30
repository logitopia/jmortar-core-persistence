/**
 * Title: DataAccessObjectImplAddEntityTest.java
 *
 * Author: Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt; Date Created: 29-May-2016
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.impl.DataAccessObjectImpl;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.impl.fixtures.AddEntityParentModel;
import com.logitopia.jmortar.core.persistence.mock.ParentModel;
import com.logitopia.jmortar.core.persistence.mock.Shoe;
import com.logitopia.jmortar.core.persistence.response.DataResponse;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * The <tt>DataAccessObjectImplAddEntityTest</tt> unit test class is a unit test of the <tt>addEntity</tt> method of the
 * <tt>DataAccessObjectImpl</tt> class. This will test adding an entity to persistent storage from point of request to
 * call to the data access component.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class DataAccessObjectImplAddEntityTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DataAccessObjectImplAddEntityTest.class);

  /**
   * The ID of the test shoe.
   */
  private static final long SHOE_ID = 1L;

  /**
   * The make of the test shoe.
   */
  private static final String SHOE_MAKE = "make";

  /**
   * The model of the test shoe.
   */
  private static final String SHOE_MODEL = "model";

  /**
   * The size of the test shoe.
   */
  private static final int SHOE_SIZE = 6;

  /**
   * The implementation subject of this test.
   */
  private DataAccessObjectImpl<Shoe> subject;

  /**
   * The (mock) readable data access component for the test entity.
   */
  private ReadableDataAccessComponent<Shoe> mockReadableDAC;

  /**
   * The (mock) writable data access component for the test entity.
   */
  private WritableDataAccessComponent<Shoe> mockWritableDAC;
  
  /**
   * Test input data for shoe.
   */
  private Map<String, Object> shoeInputData;
  
  /**
   * The parent model test fixtures.
   */
  private final AddEntityParentModel parentModelTestFixtures = new AddEntityParentModel();

  /**
   * Default Constructor.
   */
  public DataAccessObjectImplAddEntityTest() {
  }

  private void initShoeInputData() {
    shoeInputData = new HashMap<>();

    shoeInputData.put("shoeid", SHOE_ID);
    shoeInputData.put("make", SHOE_MAKE);
    shoeInputData.put("model", SHOE_MODEL);
    shoeInputData.put("size", SHOE_SIZE);
  }

  /**
   * Initialise the readable data access component.
   */
  private void initMockReadableDAC() {
    mockReadableDAC = mock(ReadableDataAccessComponent.class);
  }

  /**
   * Initialise the writable data access component.
   */
  private void initMockWritableDAC() {
    mockWritableDAC = mock(WritableDataAccessComponent.class);
  }

  /**
   * Initialise the subject.
   */
  private void initSubject() {
    subject = new DataAccessObjectImpl(new HashMap<>(), mockReadableDAC, mockWritableDAC, Shoe.class);
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
    initMockReadableDAC();
    initMockWritableDAC();

    initShoeInputData();

    initSubject();

    /* Initialise test fixtures for parent test */
    parentModelTestFixtures.initialise();
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }

  /**
   * Test that, given basic positive input we get positive response.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Testing basic success for addEntity call");

    DataResponse result = subject.addEntity(shoeInputData);

    assertNotNull("Is the response null", result);
    assertEquals("Is the data response positive outcome", DataResponse.SUCCESS, result.getStatus());

    Object outcome = result.getAttribute("Shoe");
    assertNotNull("Is the entity added null", outcome);

    Shoe entity = (Shoe) outcome;
    assertNotNull("Is the specific entity type null", entity);

    /* Check values */
    assertEquals("Does the shoe ID match expected value", SHOE_ID, entity.getShoeid());
    assertNull("Check that the value that I did not update is null", entity.getMaterial());
  }

  /**
   * Test that the correct DAO gets called when multiple DAO are specified as children.
   */
  @Test
  public void testParentChildModelAdd() {
    LOG.info("Testing adding a model with child entities");
    
    DataResponse result = parentModelTestFixtures.getSubject().addEntity(
            parentModelTestFixtures.getParentModelInputData());
    assertNotNull("Is the response null", result);
    assertEquals("Is the data response positive outcome", DataResponse.SUCCESS, result.getStatus());

    Object outcome = result.getAttribute("ParentModel");
    assertNotNull("Is the entity added null", outcome);
    
    ParentModel entity = (ParentModel) outcome;
    assertNotNull("Is the specific entity type null", entity);
    
    /* Check values */
    Shoe shoeOne = entity.getChildOne();
    assertNotNull("Is child one null", shoeOne);
    assertEquals("Is the first childs ID correct", AddEntityParentModel.MULTI_SHOE_ID[0], shoeOne.getShoeid());
    
    Shoe shoeTwo = entity.getChildTwo();
    assertNotNull("Is child two null", shoeOne);
    assertEquals("Is the second childs ID correct", AddEntityParentModel.MULTI_SHOE_ID[1], shoeTwo.getShoeid());
    
    /* Confirm whether the correct DAO has actually run. */
    verify(parentModelTestFixtures.getShoeOneDAO(), times(1)).getEntity(any());
    verify(parentModelTestFixtures.getShoeTwoDAO(), times(1)).getEntity(any());
  }
}
