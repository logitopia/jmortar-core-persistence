/**
 * Title: DataAccessObjectImplGetEntity.java
 *
 * Author: Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt; 
 * Date Created: 13-Jun-2016
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.impl.DataAccessObjectImpl;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.exception.DataAccessComponentException;
import com.logitopia.jmortar.core.persistence.dao.exception.DataAccessComponentExceptionSource;
import com.logitopia.jmortar.core.persistence.mock.Shoe;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The <tt>DataAccessObjectImplGetEntityTest</tt> unit test class is a unit test of the<tt>getEntity</tt>
 * <tt>DataAccessObjectImpl</tt> module.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class DataAccessObjectImplGetEntityTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DataAccessObjectImplGetEntityTest.class);
  
  /**
   * Test shoe ID.
   */
  private static final Long SHOE_ID = 6L;
  
  /**
   * The test shoe make.
   */
  private static final String SHOE_MAKE = "Test Shoe Make";
  
  /**
   * The test shoe model.
   */
  private static final String SHOE_MODEL = "Test Shoe Model";
  
  /**
   * The subject of the test.
   */
  private DataAccessObjectImpl<Shoe> subject;

  /**
   * The mock readable data access component.
   */
  private ReadableDataAccessComponent<Shoe> mockReadableDAC;
  
  /**
   * The success test response.
   */
  private Shoe testResponse;

  /**
   * Default Constructor.
   */
  public DataAccessObjectImplGetEntityTest() {
  }

  /**
   * Initialise the mock readable data access model.
   */
  private void initMockReadableDAC() {
    mockReadableDAC = mock(ReadableDataAccessComponent.class);
    initTestResponse();
    
    try {
      when(mockReadableDAC.getEntityByID(any(Class.class), any(Shoe.class))).thenReturn(testResponse);
    } catch (DataAccessComponentException ex) {
      ex.printStackTrace();
      fail("Unable to get entity from test mock");
    }
  }

  /**
   * Initialise the subject.
   */
  private void initSubject() {
    initMockReadableDAC();
    subject = new DataAccessObjectImpl(null,
            mockReadableDAC, Shoe.class);
  }
  
  /**
   * Initialise the test DAC response.
   */
  private void initTestResponse() {
    testResponse = new Shoe();
    
    testResponse.setShoeid(SHOE_ID);
    testResponse.setMake(SHOE_MAKE);
    testResponse.setModel(SHOE_MODEL);
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
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }
  
  /**
   * Test that, given positive input, a positive response is returned.
   */
  @Test
  public void testBasicSuccess() throws DataAccessComponentException {
    LOG.info("Test basic success for getEntity.");
    
    Map<String, Object> keyValues = new HashMap<>();
    keyValues.put("shoeid", SHOE_ID);
    
    Shoe result = subject.getEntity(keyValues);
    assertNotNull("Is result null?", result);
    
    assertEquals("Check shoe make", SHOE_MAKE, result.getMake());
    assertEquals("Check shoe model", SHOE_MODEL, result.getModel());
    
    /* Check that the relevant models were called. */
    ArgumentCaptor<Shoe> shoeCaptor = ArgumentCaptor.forClass(Shoe.class);
    ArgumentCaptor<Class> classCaptor = ArgumentCaptor.forClass(Class.class);
    verify(mockReadableDAC, times(1)).getEntityByID(classCaptor.capture(), shoeCaptor.capture());
    
    Shoe capturedShoe = shoeCaptor.getValue();
    assertNotNull("Check the template that was generated.", capturedShoe);
    assertEquals("Template ID check", SHOE_ID, new Long(capturedShoe.getShoeid()));
    assertNull("Template Make check", capturedShoe.getMake());
    assertNull("Template Model check", capturedShoe.getModel());
  }
  
  /**
   * Test that, data access component exception is thrown, an expected negative response is returned.
   */
  @Test
  public void testDataAccessComponentFailure() throws DataAccessComponentException {
    LOG.info("Test data access component failure for getEntity.");
    
    when(mockReadableDAC.getEntityByID(any(Class.class), any(Shoe.class)))
            .thenThrow(new DataAccessComponentException(DataAccessComponentExceptionSource.READABLE, "MOCK"));
            
    Map<String, Object> keyValues = new HashMap<>();
    keyValues.put("shoeid", SHOE_ID);
    
    Shoe result = subject.getEntity(keyValues);
    assertNull("Is result null?", result);
    
    /* Check that the relevant models were called. */
    ArgumentCaptor<Shoe> shoeCaptor = ArgumentCaptor.forClass(Shoe.class);
    ArgumentCaptor<Class> classCaptor = ArgumentCaptor.forClass(Class.class);
    verify(mockReadableDAC, times(1)).getEntityByID(classCaptor.capture(), shoeCaptor.capture());
    
    Shoe capturedShoe = shoeCaptor.getValue();
    assertNotNull("Check the template that was generated.", capturedShoe);
    assertEquals("Template ID check", SHOE_ID, new Long(capturedShoe.getShoeid()));
    assertNull("Template Make check", capturedShoe.getMake());
    assertNull("Template Model check", capturedShoe.getModel());
  }
}
