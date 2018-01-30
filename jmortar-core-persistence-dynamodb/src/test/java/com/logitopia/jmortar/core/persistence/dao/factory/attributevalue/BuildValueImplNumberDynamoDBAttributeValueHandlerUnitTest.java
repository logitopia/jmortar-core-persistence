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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A test of the <tt>buildValueImpl</tt> unit of the <tt>NumberDynamoDBAttributeValueHandler</tt>
 * module.
 *
 * @author Stephen Cheesley
 */
public class BuildValueImplNumberDynamoDBAttributeValueHandlerUnitTest
        extends AbstractUnitTest<NumberDynamoDBAttributeValueHandler> {
  
  /**
   * The logger for this class.
   */
  private static final Logger LOG =
          LoggerFactory.getLogger(BuildValueImplNumberDynamoDBAttributeValueHandlerUnitTest.class);

  /**
   * Mock successor object.
   */
  private DynamoDBAttributeValueHandler mockSuccessor;

  public BuildValueImplNumberDynamoDBAttributeValueHandlerUnitTest() {
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
   * Test that, given positive input, that we get positive output.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test basic success");
    int testVal = 7;
    
    AttributeValue result = getSubject().buildValueImpl(testVal);
    assertNotNull("Is the result null", result);
    
    String resultVal = result.getN();
    assertNotNull("Is the result value null", resultVal);
    assertEquals("Is the result value as expected", "7", resultVal);
  }
}
