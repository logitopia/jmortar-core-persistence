/*
 * Title: UpdateHibernateTest
 *
 * Author: s.cheesley @ Logitopia Technologies
 * Date Created: Feb 19, 2014
 *
 * This code is the intelectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.impl.mock.Shoe;
import com.logitopia.jmortar.core.persistence.response.DataResponse;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The test class <tt>UpdateHibernateTest</tt> tests for the ability to update a record using the abstract hibernate
 * DAO.
 *
 * @author s.cheesley
 */
public final class UpdateLockedEntityHibernateIntegrationTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(UpdateLockedEntityHibernateIntegrationTest.class);

  /**
   * Application Context.
   */
  private final ApplicationContext context
          = new ClassPathXmlApplicationContext("app.xml");

  /**
   * The data for shoe being tested.
   */
  private final Map<String, Object> shoeData = (Map<String, Object>) context.getBean("shoeData");

  /**
   * The service being tested.
   */
  private final DataAccessObject<Shoe> shoeDAO = (DataAccessObject<Shoe>) context.getBean("shoe");

  /**
   * Default Constructor.
   */
  public UpdateLockedEntityHibernateIntegrationTest() {
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
   * Test that we can update a record that already exists within the database.
   */
  @Test
  public void testUpdateLockedHibernate() {
    String make = "Jimmy Choo";
    String model = "ChooMaster5000";

    /* Retrieve the shoe and test that it is valid */
    Map<String, Object> shoeKey = new HashMap<>();
    shoeKey.put("shoeid", 1L);
    Shoe origShoe = shoeDAO.getEntity(shoeKey);
    Shoe updShoe = shoeDAO.getEntity(shoeKey);

    /* Update the shoe */
    updShoe.setMake(make);
    updShoe.setModel(model);

    /* This one works fine */
    shoeDAO.updateEntity(updShoe);

    origShoe.setMake("StevesShoes");

    /* This one works fails */
    DataResponse response = shoeDAO.updateEntity(origShoe);
    assertEquals("Check response value", DataResponse.FAIL_LOCKED, response.getStatus());
  }
}
