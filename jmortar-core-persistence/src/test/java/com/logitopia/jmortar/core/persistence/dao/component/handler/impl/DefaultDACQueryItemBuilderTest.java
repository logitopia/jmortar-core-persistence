/**
 * Title: DefaultDACQueryItemBuilderTest.java
 *
 * Author: Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt; 
 * Date Created: 09-Oct-2015
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.component.handler.impl;

import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The <tt>DefaultDACQueryItemBuilderTest</tt> unit test class is a unit test of the <tt>DefaultDACQueryItemBuilder</tt>
 * module.
 *
 * @author &lt;Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;&gt;
 */
public class DefaultDACQueryItemBuilderTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DefaultDACQueryItemBuilderTest.class);

  /**
   * The subject of the test.
   */
  private DefaultDACQueryItemBuilder subject;
  
  /**
   * The test key items to pass to the test.
   */
  private Map<String, Object> testKeyItems;
  
  /**
   * Default Constructor.
   */
  public DefaultDACQueryItemBuilderTest() {
  }
  
  private void initKeyItems() {
    testKeyItems = new HashMap<>();
    
    testKeyItems.put("testKey", "testValue");
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
    subject = new DefaultDACQueryItemBuilder();
    initKeyItems();
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }
  
  /**
   * Test the buildItems method.
   */
  @Test
  public void testBuild() {
    LOG.info("Test build");
    
    List<QueryItem> items = subject.buildItems(testKeyItems);
    
    assertNotNull("Query item value test", items);
    
    QueryItem result = items.get(0);
    
    assertNotNull("Query item result test", result);
    
    assertEquals("Test that the key is valid", "testKey", result.getField());
    assertEquals("Test that the value is valid", "testValue", result.getValue());
  }
}
