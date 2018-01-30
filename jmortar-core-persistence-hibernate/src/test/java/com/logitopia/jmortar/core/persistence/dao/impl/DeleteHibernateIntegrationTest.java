/*
 * Title: DeleteHibernateIntegrationTest
 *
 * Author: s.cheesley @ Logitopia Technologies
 * Date Created: Feb 19, 2014
 *
 * This code is the intelectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.impl.mock.Shoe;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The test class <tt>DeleteHibernateIntegrationTest</tt> tests for the ability to delete a
 * record using the HibernateDAO.
 *
 * @author s.cheesley
 */
public final class DeleteHibernateIntegrationTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DeleteHibernateIntegrationTest.class);

  /**
   * Application Context.
   */
  private final ApplicationContext context
          = new ClassPathXmlApplicationContext("app.xml");
  
  /**
   * The data for the shoe to test.
   */
  private final Map<String, Object> shoeData = (Map<String, Object>) context.getBean("shoeData");

  /**
   * The service being tested.
   */
  private final DataAccessObject<Shoe> shoeDAO = (DataAccessObject<Shoe>) context.getBean("shoe");

  /**
   * Default Constructor.
   */
  public DeleteHibernateIntegrationTest() {
  }

  /**
   * Execute before class creation.
   */
  @BeforeClass
  public static void setUpClass() {
  }

  /**
   * Execute after class tear down.
   */
  @AfterClass
  public static void tearDownClass() {
  }

  /**
   * {@inheritDoc}
   */
  @Before
  public void setUp() {
    /* Create a shoe to be updated. */
    try {
      /* Insert the shoe */
      shoeDAO.addEntity(shoeData);
    } catch (Exception e) {
      /* Any generated exception is a fail. */
      LOG.error("Error on shoe db op", e);
      fail("An error occured during test.");
    }
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }

  /**
   * Test that we can delete the shoe that we configured in the database.
   */
  @Test
  public void testDelete() {
    try {
      /* Now delete this shoe */
      Map<String, Object> keyData = new HashMap<>();
      keyData.put("shoeid", 1L);
      shoeDAO.deleteEntity(keyData);

      /* Retrieve the shoe */
      
      Shoe oldShoe = shoeDAO.getEntity(keyData);
      assertNull("Deleted shoe test", oldShoe);
    } catch (Exception e) {
      /* Any generated exception is a fail. */
      LOG.error("Error on shoe db op", e);
      fail("An error occured during test.");
    }
  }
}
