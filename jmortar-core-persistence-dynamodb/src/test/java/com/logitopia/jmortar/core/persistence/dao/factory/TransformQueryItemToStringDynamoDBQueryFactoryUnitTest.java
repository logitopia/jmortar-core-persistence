/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory;

import com.logitopia.jmortar.core.persistence.dao.factory.attributevalue.DynamoDBAttributeValueHandler;
import com.logitopia.jmortar.core.persistence.dao.factory.attributevalue.IsValueApplicableNumberDynamoDBAttributeValueHandlerUnitTest;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryItemImpl;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemSortType;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import java.util.Map;

import com.logitopia.jmortar.core.test.exception.PrivateTestMethodException;
import org.junit.After;
import org.junit.AfterClass;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A unit test of the <tt>transformQueryItemToString</tt> unit on the <tt>DynamoDBQueryFactory</tt>
 * module.
 *
 * @author Stephen Cheesley
 */
public class TransformQueryItemToStringDynamoDBQueryFactoryUnitTest
        extends AbstractUnitTest<DynamoDBQueryFactory> {
  
  /**
   * The logger for this class.
   */
  private static final Logger LOG =
          LoggerFactory.getLogger(TransformQueryItemToStringDynamoDBQueryFactoryUnitTest.class);

  /**
   * Mock attribute value.
   */
  private DynamoDBAttributeValueHandler mockAttributeValue;
  
  public TransformQueryItemToStringDynamoDBQueryFactoryUnitTest() {
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
    setSubject(new DynamoDBQueryFactory(mockAttributeValue));
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }
  
  /**
   * Test that, given positive inputs, we get positive outputs.
   */
  @Test
  public void basicSuccessTest() {
    LOG.info("Basic Success Test");
    
    QueryItem item = new QueryItemImpl();
    item.setEntity("testEntity");
    item.setField("testField");
    item.setValue("testValue");
    item.setComparator(QueryItemComparator.EQUALS);
    item.setSortType(QueryItemSortType.NONE);

    Object resultObj = null;
    try {
      resultObj = executePrivateMethod("transformQueryItemToString",
              new Class[] {QueryItem.class}, new Object[] {item});
    } catch (PrivateTestMethodException e) {
      fail("Unable to run transformQueryItemToString method.");
    }

    assertNotNull("Is the result null", resultObj);
    assertTrue("Is the result the correct type", resultObj instanceof Map);
    
    Map<String, Object> result = (Map<String, Object>) resultObj;
    Object queryStringObj = result.get("queryItemString");
    assertEquals("Is the result the correct", "testField = :testField", queryStringObj);
    Object queryValueObj = result.get("testField");
    assertEquals("Is the result value correct", "testValue", queryValueObj);
  }
}
