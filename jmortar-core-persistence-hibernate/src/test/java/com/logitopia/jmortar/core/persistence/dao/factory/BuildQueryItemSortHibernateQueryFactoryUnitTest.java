/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory;

import com.logitopia.jmortar.core.persistence.dao.component.impl.ReadableHibernateDataAccessComponentImpl;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryItemImpl;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemSortType;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The <tt>BuildQueryItemSortHibernateQueryFactoryUnitTest</tt> is a unit test of the
 * <tt>buildQueryItemSort</tt> method on the hibernate query factory class.
 *
 * @author Stephen Cheesley
 */
public class BuildQueryItemSortHibernateQueryFactoryUnitTest
        extends AbstractUnitTest<HibernateQueryFactory> {
  
  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(BuildQueryItemSortHibernateQueryFactoryUnitTest.class);
  
  /**
   * A query item that use as an input to our test.
   */
  private QueryItem testQueryItem;

  public BuildQueryItemSortHibernateQueryFactoryUnitTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
    setSubject(new HibernateQueryFactory());
    
    testQueryItem = new QueryItemImpl();
    
    testQueryItem.setField("testField");
    testQueryItem.setSortType(QueryItemSortType.UNSPECIFIED);
  }

  @After
  public void tearDown() {
  }
  
  /**
   * Test that, given a positive input, we get a positive output.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test basic success");
    
    Object result = executePrivateMethod("buildQueryItemSort", new Class[] {QueryItem.class},
            new Object[] {testQueryItem});
    
    assertNotNull("Is the result null", result);
    assertTrue("Is the result the correct type", result instanceof String);
    
    String resultString = (String) result;
    assertEquals("Does result String match expected value", "testField", resultString);
  }

  /**
   * Test that, given a positive ascending sort input, we get a positive output.
   */
  @Test
  public void testAscSortSuccess() {
    LOG.info("Test ascending sort success");
    
    testQueryItem = new QueryItemImpl();
    
    testQueryItem.setField("testField");
    testQueryItem.setSortType(QueryItemSortType.ASC);
    
    Object result = executePrivateMethod("buildQueryItemSort", new Class[] {QueryItem.class},
            new Object[] {testQueryItem});
    
    assertNotNull("Is the result null", result);
    assertTrue("Is the result the correct type", result instanceof String);
    
    String resultString = (String) result;
    assertEquals("Does result String match expected value", "testField asc", resultString);
  }
  
  /**
   * Test that, given a positive descending sort input, we get a positive output.
   */
  @Test
  public void testDescSortSuccess() {
    LOG.info("Test descending sort success");
    
    testQueryItem = new QueryItemImpl();
    
    testQueryItem.setField("testField");
    testQueryItem.setSortType(QueryItemSortType.DESC);
    
    Object result = executePrivateMethod("buildQueryItemSort", new Class[] {QueryItem.class},
            new Object[] {testQueryItem});
    
    assertNotNull("Is the result null", result);
    assertTrue("Is the result the correct type", result instanceof String);
    
    String resultString = (String) result;
    assertEquals("Does result String match expected value", "testField desc", resultString);
  }
  
  /**
   * Test that, given a positive none sort input, we get a positive output.
   */
  @Test
  public void testNoneSortSuccess() {
    LOG.info("Test none sort success");
    
    testQueryItem = new QueryItemImpl();
    
    testQueryItem.setField("testField");
    testQueryItem.setSortType(QueryItemSortType.NONE);
    
    Object result = executePrivateMethod("buildQueryItemSort", new Class[] {QueryItem.class},
            new Object[] {testQueryItem});
    
    assertNull("Is the result null", result);
  }
}
