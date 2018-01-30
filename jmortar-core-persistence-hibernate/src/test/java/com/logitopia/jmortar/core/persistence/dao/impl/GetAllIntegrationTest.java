/*
 * Title: GetAllIntegrationTest
 * 
 * Author: s.cheesley @ Logitopia Technologies
 * Date Created: Apr 9, 2014
 * 
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.impl.mock.Shoe;
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
 * The test class <tt>GetAllIntegrationTest</tt> tests that for multiple entries in the database we can retrieve the entire record
 * set for a table.
 *
 * @author s.cheesley
 */
public class GetAllIntegrationTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(GetAllIntegrationTest.class);

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

  /*
   * Default Constructor.
   */
  public GetAllIntegrationTest() {
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
   * Test that we can get all known records stored in the database.
   */
  @Test
  public void testGetAll() {

    /* Persist the models */
    configureShoes();

    List<Shoe> shoes = SHOEDAO.getAllEntities();

    assertNotNull("List Null Test", shoes);
    LOG.info("List Size : " + shoes.size());
    assertTrue("List Size Test", shoes.size() == 3);

    for (Shoe shoe : shoes) {
      LOG.info("Shoe Found: " + shoe.getMake() + " | " + shoe.getModel());
    }
  }
  
  /**
   * Test that we can get all known records stored in the database, and that the results are in a paginated format.
   */
  @Test
  public void testGetAllPaginated() {

    /* Persist the models */
    configureShoes();

    List<Shoe> shoes = SHOEDAO.getAllEntities(1, 1);

    assertNotNull("List Null Test", shoes);
    LOG.info("List Size : " + shoes.size());
    assertTrue("List Size Test", shoes.size() == 1);

    /* Check that the shoe is the correct item for that page. */
    Shoe shoe = shoes.get(0);
    assertEquals("Is shoe correct one", "Dr Martins", shoe.getMake());
  }
}
