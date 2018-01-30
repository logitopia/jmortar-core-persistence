/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory.attributevalue;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A test of the <tt>buildValueImpl</tt> unit of the <tt>DefaultDynamoDBAttributeValueHandler</tt>
 * module.
 *
 * @author Stephen Cheesley
 */
public class BuildValueImplDefaultDynamoDBAttributeValueHandlerUnitTest
        extends AbstractUnitTest<DefaultDynamoDBAttributeValueHandler> {

  /**
   * The logger for this class.
   */
  private static final Logger LOG =
          LoggerFactory.getLogger(BuildValueImplDefaultDynamoDBAttributeValueHandlerUnitTest.class);
  
  public BuildValueImplDefaultDynamoDBAttributeValueHandlerUnitTest() {
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
    setSubject(new DefaultDynamoDBAttributeValueHandler());
  }

  @After
  public void tearDown() {
  }

  /**
   * Test that, given positive inputs, we get expected positive outputs.
   */
  @Test
  public void basicSuccessTest() {
    LOG.info("Basic Success Test");
    String testAttributeValue = "TEST_VALUE";
    
    Object rawResult = getSubject().buildValueImpl(testAttributeValue);
    
    assertNotNull("Is the result null", rawResult);
    assertTrue("Is the result the correct type", rawResult instanceof AttributeValue);
    
    AttributeValue result = (AttributeValue) rawResult;
    String value = result.getS();
    assertEquals("Is the attribute value as expected", testAttributeValue, value);
  }
}
