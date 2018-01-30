/*
 * Title: TestGetAll
 * 
 * Author: s.cheesley @ Logitopia Technologies
 * Date Created: Apr 9, 2014
 * 
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryImpl;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryItemImpl;
import com.logitopia.jmortar.core.persistence.dao.impl.mock.Shoe;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryLogicalConjunction;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The test class <tt>FindIntegrationTest</tt> tests that for multiple entries in the database we can retrieve
 * a desired subset of records for a table.
 *
 * @author s.cheesley
 */
public class FindIntegrationTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(FindIntegrationTest.class);

  /**
   * Application Context.
   */
  private final ApplicationContext context
          = new ClassPathXmlApplicationContext("app.xml");

  /**
   * Shoe one.
   */
  private final Map<String, Object> SHOE1 = (Map<String, Object>) context.getBean("GAShoe1");

  /**
   * Shoe two.
   */
  private final Map<String, Object> SHOE2 = (Map<String, Object>) context.getBean("GAShoe2");

  /**
   * Shoe three.
   */
  private final Map<String, Object> SHOE3 = (Map<String, Object>) context.getBean("GAShoe3");

  /**
   * The service being tested.
   */
  private final DataAccessObject<Shoe> SHOEDAO = (DataAccessObject<Shoe>) context.getBean("shoe");

  /**
   * A test query to get a specific subset of shoes.
   */
  private Query testQuery;

  /*
   * Default Constructor.
   */
  public FindIntegrationTest() {
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
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }

  /**
   * Configure the shoes to be used as part of this unit testing.
   */
  private void configureShoes() {
    /* Insert the shoes for use with testing. */
    SHOEDAO.addEntity(SHOE1);
    SHOEDAO.addEntity(SHOE2);
    SHOEDAO.addEntity(SHOE3);
  }

  /**
   * Configure the query to be used by the test.
   */
  private void configureQuery() {
    testQuery = new QueryImpl();

    QueryItem item = new QueryItemImpl();
    item.setField("material");
    item.setValue("leather");
    item.setComparator(QueryItemComparator.EQUALS);
    item.setNot(false);

    testQuery.addQuery(item);
    testQuery.setQueryType(QueryLogicalConjunction.AND);
  }

  /**
   * Configure the query to be used by the SQL injection test.
   */
  private void configureQuerySQLInjection() {
    testQuery = new QueryImpl();

    QueryItem item = new QueryItemImpl();
    item.setField("make");
    item.setValue("Nike' OR make = 'Dr Martins");
    item.setComparator(QueryItemComparator.EQUALS);
    item.setNot(false);

    testQuery.addQuery(item);
    testQuery.setQueryType(QueryLogicalConjunction.AND);
  }

  /**
   * Test that we can find known records stored in the database.
   */
  @Test
  public void testFind() {

    /* Persist the models */
    configureShoes();

    /* Calibrate the query. */
    configureQuery();

    List<Shoe> shoes = SHOEDAO.findEntities(testQuery);

    assertNotNull("List Null Test", shoes);
    LOG.info("List Size : " + shoes.size());
    assertTrue("List Size Test", shoes.size() == 2);

    for (Shoe shoe : shoes) {
      LOG.info("Shoe Found: " + shoe.getMake() + " | " + shoe.getModel());
    }
  }

  /**
   * Test that we cannot make an SQL injection attack using the find functionalities passed query.
   */
  @Test
  public void testFindSQLInjection() {

    /* Persist the models */
    configureShoes();

    /* Calibrate the query. */
    configureQuerySQLInjection();

    List<Shoe> shoes = SHOEDAO.findEntities(testQuery);

    assertNotNull("List Null Test", shoes);
    LOG.info("List Size : " + shoes.size());
    assertEquals("List size test", 0, shoes.size());

    for (Shoe shoe : shoes) {
      LOG.info("Shoe Found: " + shoe.getMake() + " | " + shoe.getModel());
    }
  }

  /**
   * Test that we can find known records stored in the database, and that the results are in a
   * paginated format.
   */
  @Test
  public void testFindPaginated() {

    /* Persist the models */
    configureShoes();

    /* Calibrate the query. */
    configureQuery();

    List<Shoe> shoes = SHOEDAO.findEntities(testQuery, 0, 1);

    assertNotNull("List Null Test", shoes);
    LOG.info("List Size : " + shoes.size());
    assertTrue("List Size Test", shoes.size() == 1);

    /* Check that the shoe is the correct item for that page. */
    Shoe shoe = shoes.get(0);
    assertEquals("Is shoe correct one", "Dr Martins", shoe.getMake());
  }
}
