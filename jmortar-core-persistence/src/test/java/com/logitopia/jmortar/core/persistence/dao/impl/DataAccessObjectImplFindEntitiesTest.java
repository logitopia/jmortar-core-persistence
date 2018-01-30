/**
 * Title: DataAccessObjectImplFindEntities.java
 *
 * Author: Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt; Date Created: 14-Jun-2016
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.mock.Shoe;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The <tt>DataAccessObjectImplFindEntitiesTest</tt> unit test class is a unit test of the<tt>findEntities</tt>
 * <tt>DataAccessObjectImpl</tt> module.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class DataAccessObjectImplFindEntitiesTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DataAccessObjectImplFindEntitiesTest.class);
  
  /**
   * The IDs for shoes to be returned from getAll.
   */
  private static final Long[] SHOE_IDS = {4L, 5L, 6L, 7L};
  
  /**
   * The subject of the test.
   */
  private DataAccessObjectImpl subject;

  /**
   * The mock readable data access component.
   */
  private ReadableDataAccessComponent<Shoe> mockReadableDAC;

  /**
   * The result to be returned from the mock DAC.
   */
  private List<Shoe> testFindResponse;
  
  /**
   * The result to be returned from the paged find request.
   */
  private List<Shoe> testFindPagedResponse;

  /**
   * Default Constructor.
   */
  public DataAccessObjectImplFindEntitiesTest() {
  }
  
  /**
   * Initialise the mock readable data access model.
   */
  private void initMockReadableDAC() {
    mockReadableDAC = mock(ReadableDataAccessComponent.class);
    initTestFindResponse();
    initTestFindPagedResponse();

    when(mockReadableDAC.find(any(Class.class), any(Query.class))).thenReturn(testFindResponse);
    when(mockReadableDAC.find(any(Class.class), any(Query.class), eq(2), eq(2))).thenReturn(testFindPagedResponse);
  }

  /**
   * Initialise the getAll response.
   */
  private void initTestFindResponse() {
    testFindResponse = new ArrayList<>();

    for (Long id : SHOE_IDS) {
      Shoe shoe = new Shoe();
      shoe.setShoeid(id);
      testFindResponse.add(shoe);
    }
  }
  
  /**
   * Initialise the getAll paged response.
   */
  private void initTestFindPagedResponse() {
    testFindPagedResponse = new ArrayList<>();

    Shoe shoe = new Shoe();
    shoe.setShoeid(SHOE_IDS[2]);
    testFindPagedResponse.add(shoe);
    
    Shoe shoe1 = new Shoe();
    shoe1.setShoeid(SHOE_IDS[3]);
    testFindPagedResponse.add(shoe1);
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
   * Test basic success scenario.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test that we can find entities.");
    
    Query testQuery = new QueryImpl();
    List<Shoe> result = subject.findEntities(testQuery);
    
    assertNotNull("Is result null?", result);
    assertTrue("Is the result to correct type", result instanceof List);
    
    assertEquals("Check the number of results returned", 4, result.size());
  }
  
  /**
   * Test basic success scenario.
   */
  @Test
  public void testPaginatedSuccess() {
    LOG.info("Test that we can find entities and return a paginated result.");
    
    Query testQuery = new QueryImpl();
    List<Shoe> result = subject.findEntities(testQuery, 2, 2);
    
    assertNotNull("Is result null?", result);
    assertTrue("Is the result to correct type", result instanceof List);
    
    assertEquals("Check the number of results returned", 2, result.size());
    verify(mockReadableDAC, times(1)).find(any(Class.class), any(Query.class), eq(2), eq(2));
  }
  
  /**
   * Test that, we get an expected result, when the data access component is not readable.
   */
  @Test
  public void testFailNotReadable() {
    LOG.info("Test fail on DAO not readable");

    subject = new DataAccessObjectImpl((Map) null, (ReadableDataAccessComponent) null, Shoe.class);

    Query testQuery = new QueryImpl();
    List<Shoe> result = subject.findEntities(testQuery);
    
    assertNotNull("Is result not null", result);
    assertEquals("Is list empty", 0, result.size());
  }
}
