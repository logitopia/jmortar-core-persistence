/**
 * Title: DataAccessObjectImplGetAllEntities.java
 *
 * Author: Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt; Date Created: 06-Jun-2016
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.impl.DataAccessObjectImpl;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.mock.Shoe;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The <tt>DataAccessObjectImplGetAllEntitiesTest</tt> unit test class is a unit test of the<tt>getAllEntities</tt>
 * <tt>DataAccessObjectImpl</tt> module.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class DataAccessObjectImplGetAllEntitiesTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DataAccessObjectImplGetAllEntitiesTest.class);

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
  private List<Shoe> testGetAllResponse;

  /**
   * The result to be returned from the paged getAll request.
   */
  private List<Shoe> testGetAllPagedResponse;

  /**
   * Default Constructor.
   */
  public DataAccessObjectImplGetAllEntitiesTest() {
  }

  /**
   * Initialise the mock readable data access model.
   */
  private void initMockReadableDAC() {
    mockReadableDAC = mock(ReadableDataAccessComponent.class);
    initTestGetAllResponse();
    initTestGetAllPagedResponse();

    when(mockReadableDAC.getAll(Shoe.class)).thenReturn(testGetAllResponse);
    when(mockReadableDAC.getAll(Shoe.class, 2, 2)).thenReturn(testGetAllPagedResponse);
  }

  /**
   * Initialise the getAll response.
   */
  private void initTestGetAllResponse() {
    testGetAllResponse = new ArrayList<>();

    for (Long id : SHOE_IDS) {
      Shoe shoe = new Shoe();
      shoe.setShoeid(id);
      testGetAllResponse.add(shoe);
    }
  }

  /**
   * Initialise the getAll paged response.
   */
  private void initTestGetAllPagedResponse() {
    testGetAllPagedResponse = new ArrayList<>();

    Shoe shoe = new Shoe();
    shoe.setShoeid(SHOE_IDS[2]);
    testGetAllPagedResponse.add(shoe);
    
    Shoe shoe1 = new Shoe();
    shoe1.setShoeid(SHOE_IDS[3]);
    testGetAllPagedResponse.add(shoe1);
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
   * Test that the <tt>getAllEntites</tt> method returns a list of the configured entities.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test that we can retrieve all entities.");

    List<Shoe> result = subject.getAllEntities();

    assertNotNull("Is result null?", result);
    assertTrue("Is the result to correct type", result instanceof List);

    assertEquals("Check the number of results returned", 4, result.size());
  }

  /**
   * Test that the <tt>getAllEntites</tt> method returns a list of the configured entities in required page sizes.
   */
  @Test
  public void testPaginatedSuccess() {
    LOG.info("Test that we can retrieve all entities.");

    List<Shoe> result = subject.getAllEntities(2, 2);

    assertNotNull("Is result null?", result);
    assertTrue("Is the result to correct type", result instanceof List);

    assertEquals("Check the number of results returned", 2, result.size());
    verify(mockReadableDAC, times(1)).getAll(Shoe.class, 2, 2);
  }

  /**
   * Test that, we get an expected result, when the data access component is not readable.
   */
  @Test
  public void testFailNotReadable() {
    LOG.info("Test fail on DAO not readable");

    subject = new DataAccessObjectImpl((Map) null, (ReadableDataAccessComponent) null, Shoe.class);

    List<Shoe> result = subject.getAllEntities();
    assertNotNull("Is result not null", result);
    assertEquals("Is list empty", 0, result.size());
  }
}
