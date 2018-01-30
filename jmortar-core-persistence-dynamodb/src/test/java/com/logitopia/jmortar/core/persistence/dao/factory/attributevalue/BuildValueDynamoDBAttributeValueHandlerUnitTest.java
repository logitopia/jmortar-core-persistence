/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory.attributevalue;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A test of the <tt>BuildValue</tt> unit of the <tt>DynamoDBAttributeValueHandler</tt> module. This
 * test uses the Number implementation to test the abstract method.
 *
 * @author Stephen Cheesley
 */
public class BuildValueDynamoDBAttributeValueHandlerUnitTest
        extends AbstractUnitTest<NumberDynamoDBAttributeValueHandler> {
  
  /**
   * The logger for this class.
   */
  private static final Logger LOG =
          LoggerFactory.getLogger(BuildValueDynamoDBAttributeValueHandlerUnitTest.class);

  /**
   * Mock successor object.
   */
  private DynamoDBAttributeValueHandler mockSuccessor;

  public BuildValueDynamoDBAttributeValueHandlerUnitTest() {
  }

  /**
   * Initialise the mock successor.
   */
  private void initMockSuccessor() {
    mockSuccessor = mock(DynamoDBAttributeValueHandler.class);
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
    initMockSuccessor();
    setSubject(new NumberDynamoDBAttributeValueHandler(mockSuccessor));
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }

  /**
   * Test that, given basic positive inputs, we get positive outputs.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test basic success");
    int testVal = 23;

    AttributeValue resultObj = getSubject().buildValue(testVal);
    assertNotNull("Is the result obj null", resultObj);

    String result = resultObj.getN();
    assertNotNull("Is the result null", result);
  }

  /**
   * Test that, given a that the value is invalid, that a successor is called.
   */
  @Test
  public void testSuccessorCall() {
    LOG.info("Test successor call");
    when(mockSuccessor.isValueApplicable(any(Object.class))).thenReturn(true);
    when(mockSuccessor.buildValue(any(String.class))).thenReturn(new AttributeValue());
    
    String testVal = "testValue";

    AttributeValue resultObj = getSubject().buildValue(testVal);
    assertNotNull("Is the result obj null", resultObj);
    
    verify(mockSuccessor, times(1)).buildValue(any(Object.class));
  }
}
