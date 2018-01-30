/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory.attributevalue;

import com.logitopia.jmortar.core.test.AbstractUnitTest;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A test of the <tt>IsValueApplicable</tt> unit of the <tt>NumberDynamoDBAttributeValueHandler</tt>
 * module.
 *
 * @author Stephen Cheesley
 */
public class IsValueApplicableNumberDynamoDBAttributeValueHandlerUnitTest
        extends AbstractUnitTest<NumberDynamoDBAttributeValueHandler> {
  
  /**
   * The logger for this class.
   */
  private static final Logger LOG =
          LoggerFactory.getLogger(IsValueApplicableNumberDynamoDBAttributeValueHandlerUnitTest.class);

  /**
   * Mock successor object.
   */
  private DynamoDBAttributeValueHandler mockSuccessor;
  
  public IsValueApplicableNumberDynamoDBAttributeValueHandlerUnitTest() {
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
   * Test that, given positive input, we get positive output.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test basic success");
    int testValue = 8;
    
    boolean result = getSubject().isValueApplicable(testValue);
    assertTrue("Is the result positive", result);
  }
  
  /**
   * Test that, given basic negative input, we get expected negative output.
   */
  @Test
  public void testIncorrectType() {
    LOG.info("Test incorrect type");
    String testValue = "invalid";
    
    boolean result = getSubject().isValueApplicable(testValue);
    assertFalse("Is the result negative", result);
  }
  
  /**
   * Test that, given basic negative input, we get expected negative output.
   */
  @Test
  public void testNullType() {
    LOG.info("Test null type");
    String testValue = null;
    
    boolean result = getSubject().isValueApplicable(testValue);
    assertFalse("Is the result negative", result);
  }
}
