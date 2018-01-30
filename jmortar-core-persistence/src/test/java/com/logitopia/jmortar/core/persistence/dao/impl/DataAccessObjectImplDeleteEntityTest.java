/**
 * Title: DataAccessObjectImplDeleteEntityTest.java
 *
 * Author: Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt; 
 * Date Created: 04-Jun-2016
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.impl.DataAccessObjectImpl;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.exception.DataAccessComponentException;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteLockingException;
import com.logitopia.jmortar.core.persistence.mock.Shoe;
import com.logitopia.jmortar.core.persistence.response.DataResponse;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The <tt>DataAccessObjectImplDeleteEntityTest</tt> unit test class is a unit test of the<tt>deleteEntity</tt>
 * <tt>DataAccessObjectImpl</tt> module.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class DataAccessObjectImplDeleteEntityTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DataAccessObjectImplDeleteEntityTest.class);

  public static final Long SHOE_ID = 4L;

  public static final String SHOE_MAKE = "Nike";

  public static final String SHOE_MODEL = "Air Max";

  /**
   * The subject of the test.
   */
  private DataAccessObjectImpl subject;

  /**
   * The mock readable data access component.
   */
  private ReadableDataAccessComponent<Shoe> mockReadableDAC;

  /**
   * The mock writable data access component.
   */
  private WritableDataAccessComponent<Shoe> mockWritableDAC;

  /**
   * The model to test deletion of.
   */
  private Shoe testShoe;

  /**
   * Default Constructor.
   */
  public DataAccessObjectImplDeleteEntityTest() {
  }

  /**
   * Initialise the test model.
   */
  private void initTestShoe() {
    testShoe = new Shoe();
    testShoe.setShoeid(SHOE_ID);
    testShoe.setMake(SHOE_MAKE);
    testShoe.setModel(SHOE_MODEL);
  }

  /**
   * Initialise the mock readable data access model.
   */
  private void initMockReadableDAC() {
    mockReadableDAC = mock(ReadableDataAccessComponent.class);
    try {
      when(mockReadableDAC.getEntityByID(any(Class.class), any(Shoe.class))).thenReturn(testShoe);
    } catch (DataAccessComponentException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Initialise the mock writable data access component.
   */
  private void initMockWritableDAC() {
    mockWritableDAC = mock(WritableDataAccessComponent.class);
  }

  /**
   * Initialise the subject.
   */
  private void initSubject() {
    initMockReadableDAC();
    initMockWritableDAC();
    subject = new DataAccessObjectImpl(null,
            mockReadableDAC, mockWritableDAC, Shoe.class);
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
    initSubject();

    initTestShoe();
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }

  /**
   * Test that, with positive inputs, the delete operation is successful.
   */
  @Test
  public void testBasicSuccess() throws DataAccessComponentException {
    LOG.info("Test Basic Success");

    when(mockReadableDAC.getEntityByID(any(Class.class), any(Shoe.class))).thenReturn(testShoe);
    Map<String, Object> deleteData = new HashMap<>();
    deleteData.put("shoeid", SHOE_ID);

    DataResponse result = subject.deleteEntity(deleteData);
    assertNotNull("Is result not null", result);
    assertEquals("Was the result successful", DataResponse.SUCCESS, result.getStatus());
  }

  /**
   * Test that, when we attempt to delete an entity, we get an expected failure if the entity is not present in the
   * database.
   */
  @Test
  public void testFailOnNullEntity() throws DataAccessComponentException {
    LOG.info("Test fail on null entity");

    when(mockReadableDAC.getEntityByID(any(Class.class), any(Shoe.class))).thenReturn(null);

    Map<String, Object> deleteData = new HashMap<>();
    deleteData.put("shoeid", SHOE_ID);

    DataResponse result = subject.deleteEntity(deleteData);
    assertNotNull("Is result not null", result);
    assertEquals("Was the result successful", DataResponse.FAIL_GENERAL, result.getStatus());

    /* Check the message */
    String message = result.getAttribute("message").toString();
    assertEquals("Does the message match our expectations", "Unable to find record to be deleted", message);
  }
  
  /**
   * Test for expected failure output when the DAO is not setup to be writable.
   */
  @Test
  public void testFailNotWritable() {
    LOG.info("Test fail on DAO not writable");
    
    subject = new DataAccessObjectImpl(null,
            mockReadableDAC, Shoe.class);
    
    Map<String, Object> deleteData = new HashMap<>();
    deleteData.put("shoeid", SHOE_ID);

    DataResponse result = subject.deleteEntity(deleteData);
    assertNotNull("Is result not null", result);
    assertEquals("Was the result successful", DataResponse.FAIL_GENERAL, result.getStatus());
    
    /* Check the message. */
    String message = result.getAttribute("message").toString();
    assertEquals("Does the message match our expectations", "This type is not writable", message);
  }
  
  /**
   * Test that the DAO returns the expected failure for a locked entity.
   */
  @Test
  public void testFailLockedEntity() throws DataAccessWriteLockingException, DataAccessComponentException {
    LOG.info("Test fail on locked entity.");
    
    when(mockReadableDAC.getEntityByID(any(Class.class), any(Shoe.class))).thenReturn(testShoe);
    DataAccessWriteLockingException exception = mock(DataAccessWriteLockingException.class);
    doThrow(exception).when(mockWritableDAC).delete(testShoe);
    
    Map<String, Object> deleteData = new HashMap<>();
    deleteData.put("shoeid", SHOE_ID);

    DataResponse result = subject.deleteEntity(deleteData);
    assertNotNull("Is result not null", result);
    assertEquals("Was the result successful", DataResponse.FAIL_LOCKED, result.getStatus());
  }
}
