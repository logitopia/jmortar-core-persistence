/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.component.model.Report;
import com.logitopia.jmortar.core.persistence.dao.component.model.ReportType;
import com.logitopia.jmortar.core.persistence.response.DataResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
 * The <tt>DynamoDBDataAccessComponentIntegrationTest</tt> is an integration test that connects to a
 * test DynamoDB and tests the data access components.
 *
 * @author Stephen Cheesley
 */
public final class DynamoDBDataAccessComponentIntegrationTest {
  
  /**
   * Application Context.
   */
  private final ApplicationContext context
          = new ClassPathXmlApplicationContext("app.xml");
  
  /**
   * The service being tested.
   */
  private final DataAccessObject<Report> REPORTDAO
          = (DataAccessObject<Report>) context.getBean("report");

  public DynamoDBDataAccessComponentIntegrationTest() {
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
   * Test that, given positive inputs, we get positive output.
   */
  @Test
  public void basicSuccess() {
    Date reportTime = new Date();
    
    ReportType type = new ReportType();
    type.setEnabled(reportTime);
    type.setType("Automobile");
    type.setTypeValue(4);
    
    Map<String, Object> newEntityValues = new HashMap<>();
    newEntityValues.put("reportId", 1L);
    newEntityValues.put("lineName", "M20");
    newEntityValues.put("status", 1);
    newEntityValues.put("time", reportTime);
    newEntityValues.put("type", type);
    
    DataResponse response = REPORTDAO.addEntity(newEntityValues);
    assertEquals("Is the response as expected", DataResponse.SUCCESS, response.getStatus());
    
    Map<String, Object> newKeyValues = new HashMap<>();
    newKeyValues.put("reportId", 1L);
    
    Report report = REPORTDAO.getEntity(newKeyValues);
    assertNotNull("Is the report null", report);
    ReportType rtResult = report.getType();
    assertEquals("Does the report type match", 4, rtResult.getTypeValue());
    
    DataResponse deleteResponse = REPORTDAO.deleteEntity(newKeyValues);
    assertEquals("Is the deletion response as expected", DataResponse.SUCCESS, 
            deleteResponse.getStatus());
  }
}
