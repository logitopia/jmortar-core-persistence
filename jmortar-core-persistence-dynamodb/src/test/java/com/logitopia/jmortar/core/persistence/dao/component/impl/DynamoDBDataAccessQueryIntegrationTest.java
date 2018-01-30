/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.component.model.TestMusic;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryImpl;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryItemImpl;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryLogicalConjunction;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * An integration test of the query and sort abilities of the data access components written for
 * Dynamo DB.
 *
 * @author Stephen Cheesley
 */
public class DynamoDBDataAccessQueryIntegrationTest {
  
  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DynamoDBDataAccessQueryIntegrationTest.class);
  
  /**
   * Application Context.
   */
  private final ApplicationContext context
          = new ClassPathXmlApplicationContext("app.xml");
  
  /**
   * The service being tested.
   */
  private final DataAccessObject<TestMusic> DAO
          = (DataAccessObject<TestMusic>) context.getBean("testMusic");

  /**
   * The service being tested.
   */
  private final List<Map<String, Object>> FIXTURES
          = (List<Map<String, Object>>) context.getBean("fixtures");
  
  public DynamoDBDataAccessQueryIntegrationTest() {
    for (Map<String, Object> music:FIXTURES) {
      DAO.addEntity(music);
    }
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Test that, given positive inputs we receive positive outputs.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test basic success");
    
    QueryItem item = new QueryItemImpl();
    item.setField("artist");
    item.setValue("Bob Marley");
    item.setComparator(QueryItemComparator.EQUALS);
    
    Query query = new QueryImpl();
    query.addQuery(item);
    query.setQueryType(QueryLogicalConjunction.AND);
    
    List<TestMusic> items = DAO.findEntities(query);
    
    assertNotNull("Is the items list null", items);
    assertEquals("Is the items list the correct size", 4, items.size());
    
    TestMusic first = items.get(0);
    assertEquals("Does the first item match expectation", "Exodus", first.getSongTitle());
    
    TestMusic last = items.get(3);
    assertEquals("Does the last item match expectation", "Waiting In Vain", last.getSongTitle());
  }
  
  /**
   * Test that, given positive inputs, the response returns a paged response.
   */
  @Test
  public void testPaging() {
    LOG.info("Test paging success");
    
    QueryItem item = new QueryItemImpl();
    item.setField("artist");
    item.setValue("Bob Marley");
    item.setComparator(QueryItemComparator.EQUALS);
    
    Query query = new QueryImpl();
    query.addQuery(item);
    query.setQueryType(QueryLogicalConjunction.AND);
    
    List<TestMusic> items = DAO.findEntities(query, 1, 2);
    
    assertNotNull("Is the items list null", items);
    assertEquals("Is the items list the correct size", 2, items.size());
    
    TestMusic first = items.get(0);
    assertEquals("Does the first item match expectation", "Exodus", first.getSongTitle());
    
    TestMusic last = items.get(1);
    assertEquals("Does the last item match expectation", "Jamming", last.getSongTitle());
    
    items = DAO.findEntities(query, 2, 2);
    
    assertNotNull("Is the items list null", items);
    assertEquals("Is the items list the correct size", 2, items.size());
    
    first = items.get(0);
    assertEquals("Does the first item match expectation", "Three Little Birds", first.getSongTitle());
    
    last = items.get(1);
    assertEquals("Does the last item match expectation", "Waiting In Vain", last.getSongTitle());
  }
  
  /**
   * Test that, given positive inputs, the response returns a paged response.
   */
  @Test
  public void testGetAllPaging() {
    LOG.info("Test paging success");
    
    List<TestMusic> items = DAO.getAllEntities(1, 3);
    
    assertNotNull("Is the items list null", items);
    assertEquals("Is the items list the correct size", 3, items.size());
    
    TestMusic first = items.get(0);
    assertEquals("Does the first item match expectation", "Exodus", first.getSongTitle());
    
    TestMusic last = items.get(2);
    assertEquals("Does the last item match expectation", "Three Little Birds", last.getSongTitle());
    
    items = DAO.getAllEntities(2, 3);
    
    assertNotNull("Is the items list null", items);
    assertEquals("Is the items list the correct size", 3, items.size());
    
    first = items.get(0);
    assertEquals("Does the first item match expectation", "Waiting In Vain", first.getSongTitle());
    
    last = items.get(2);
    assertEquals("Does the last item match expectation", "Im Fat", last.getSongTitle());
  }
}
