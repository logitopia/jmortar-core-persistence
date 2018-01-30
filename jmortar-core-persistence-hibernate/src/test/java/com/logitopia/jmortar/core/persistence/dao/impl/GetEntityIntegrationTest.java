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
import com.logitopia.jmortar.core.persistence.dao.impl.mock.Shoe;
import java.util.HashMap;
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
 * The test class <tt>GetEntityIntegrationTest</tt> tests that for entries in the database
 * we can retrieve the entire record set for a table.
 *
 * @author s.cheesley
 */
public class GetEntityIntegrationTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(GetEntityIntegrationTest.class);

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
  public GetEntityIntegrationTest() {
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

  @Test
  public void testBasic() {
    assertTrue(true);
  }

  /**
   * Test that we can get all known records stored in the database.
   */
  @Test
  public void testGetEntityByStringId() {
    LOG.info("Test getEntity using Shoe data access object.");
    /* Persist the models */
    configureShoes();
    
    Map<String, Object> keyData = new HashMap<>();
    keyData.put("shoeid", 2);
    
    Shoe result = SHOEDAO.getEntity(keyData);
    
    assertNotNull("Is the shoe found null", result);
    LOG.info("Shoe Found: " + result.getMake() + " | " + result.getModel());
    
    assertEquals("Has the correct shoe been returned", "Dr Martins", result.getMake());
  }

}
