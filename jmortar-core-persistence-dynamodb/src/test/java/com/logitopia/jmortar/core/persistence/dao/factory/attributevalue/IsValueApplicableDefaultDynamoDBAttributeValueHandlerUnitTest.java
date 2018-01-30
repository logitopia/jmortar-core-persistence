/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory.attributevalue;

import com.logitopia.jmortar.core.test.AbstractUnitTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A test of the <tt>IsValueApplicable</tt> unit of the
 * <tt>DefaultDynamoDBAttributeValueHandler</tt> module.
 *
 * @author Stephen Cheesley
 */
public class IsValueApplicableDefaultDynamoDBAttributeValueHandlerUnitTest
        extends AbstractUnitTest<DefaultDynamoDBAttributeValueHandler> {
  
  /**
   * The logger for this class.
   */
  private static final Logger LOG =
          LoggerFactory.getLogger(IsValueApplicableDefaultDynamoDBAttributeValueHandlerUnitTest.class);

  public IsValueApplicableDefaultDynamoDBAttributeValueHandlerUnitTest() {
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
    setSubject(new DefaultDynamoDBAttributeValueHandler());
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }
  
  /**
   * Test that, given basic successful input, we get expected positive output.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test basic success");
    String testValue = "TEST_VALUE";
    
    boolean result = getSubject().isValueApplicable(testValue);
    
    assertTrue("Is the result value as expected", result);
  }

  /**
   * The <tt>isValueApplicable</tt> method should return true under any circumstances.
   */
  @Test
  public void testNullObject() {
    LOG.info("Test null object success");
    String testValue = null;
    
    boolean result = getSubject().isValueApplicable(testValue);
    
    assertTrue("Is the result value as expected", result);
  }
}
