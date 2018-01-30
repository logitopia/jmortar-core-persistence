/*
 * Title: InsertHibernateIntegrationTest
 *
 * Author: s.cheesley @ Logitopia Technologies
 * Date Created: Jan 22, 2014
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.impl.mock.ManualIdModel;
import com.logitopia.jmortar.core.persistence.dao.impl.mock.Shoe;
import com.logitopia.jmortar.core.persistence.response.DataResponse;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The <tt>InsertHibernateIntegrationTest</tt> class tests to see if we can successfully insert data into the database.
 *
 * @author s.cheesley
 */
public final class InsertHibernateIntegrationTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(InsertHibernateIntegrationTest.class);

  /**
   * Application Context.
   */
  private final ApplicationContext context
          = new ClassPathXmlApplicationContext("app.xml");
  
  /**
   * The data of the model being tested.
   */
  private final Map<String, Object> shoeData = (Map<String, Object>) context.getBean("shoeData");

  /**
   * The service being tested.
   */
  private final DataAccessObject<Shoe> shoeDAO = (DataAccessObject<Shoe>) context.getBean("shoe");

  /**
   * The data of the model being tested.
   */
  private final Map<String, Object> manualIdData = (Map<String, Object>) context.getBean("manualIdData");

  /**
   * The duplicate entry test DAO.
   */
  private final DataAccessObject<ManualIdModel> manualDAO = (DataAccessObject<ManualIdModel>) context.getBean("manualIdModel");

  /**
   * Default Constructor.
   */
  public InsertHibernateIntegrationTest() {
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
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }

  /**
   * Test that we can insert a shoe into the database.
   */
  @Test
  public void insertAndSelectShoe() {
    try {
      /* Insert the shoe */
      shoeDAO.addEntity(shoeData);

      /* Retrieve the shoe */
      Map<String, Object> keyData = new HashMap<>();
      keyData.put("shoeid", 1);
      Shoe newShoe = shoeDAO.getEntity(keyData);
      assertTrue("Shoe make test", newShoe.getMake().equals("Nike"));
    } catch (Exception e) {
      /* Any generated exception is a fail. */
      LOG.error("Error on shoe db op", e);
      fail("An error occured during test.");
    }
  }

  /**
   * Test that we can insert a manualIdModel into the database.
   */
  @Test
  public void insertAndSelectManualIdModel() {
    try {
      /* Insert the model */
      manualDAO.addEntity(manualIdData);

      /* Retrieve the shoe */
      Map<String, Object> keyData = new HashMap<>();
      keyData.put("id", "A");
      ManualIdModel newModel = manualDAO.getEntity(keyData);
      assertTrue("Manual model name test", newModel.getName().equals("test"));

      /* Attempt to save duplicate */
      DataResponse response = manualDAO.addEntity(manualIdData);
      assertEquals("Check that appropriate failure is received.", DataResponse.FAIL_DUPLICATE, response.getStatus());
    } catch (Exception e) {
      /* Any generated exception is a fail. */
      LOG.error("Error on manual db op", e);
      fail("An error occured during test.");
    }
  }
}
