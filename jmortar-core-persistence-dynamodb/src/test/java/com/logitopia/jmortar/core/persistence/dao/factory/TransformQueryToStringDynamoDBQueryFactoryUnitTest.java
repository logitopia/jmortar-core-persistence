/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory;

import com.logitopia.jmortar.core.persistence.dao.factory.attributevalue.DynamoDBAttributeValueHandler;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryImpl;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryItemImpl;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryLogicalConjunction;
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
 * A unit test of the <tt>transformQueryToString</tt> unit on the <tt>DynamoDBQueryFactory</tt>
 * module.
 *
 * @author Stephen Cheesley
 */
public class TransformQueryToStringDynamoDBQueryFactoryUnitTest
        extends AbstractUnitTest<DynamoDBQueryFactory> {

  /**
   * The logger for this class.
   */
  private static final Logger LOG =
          LoggerFactory.getLogger(TransformQueryToStringDynamoDBQueryFactoryUnitTest.class);
  
  /**
   * Query item test fields.
   */
  private static final String[] QUERY_ITEM_FIELD = {"name", "age"};

  /**
   * Query Item test values.
   */
  private static final Object[] QUERY_ITEM_VALUE = {"Smith", 21};

  /**
   * Query Item test comparator.
   */
  private static final QueryItemComparator[] QUERY_ITEM_COMP = {QueryItemComparator.EQUALS,
    QueryItemComparator.MORE_EQ};

  /**
   * Mock attribute value.
   */
  private DynamoDBAttributeValueHandler mockAttributeValue;

  /**
   * The query to test the private method with.
   */
  private Query testQuery;

  public TransformQueryToStringDynamoDBQueryFactoryUnitTest() {
  }

  /**
   * Initialise Test Query.
   */
  private void initTestQuery() {
    testQuery = new QueryImpl();

    testQuery.setQueryType(QueryLogicalConjunction.AND);
    
    for (int i=0;i<QUERY_ITEM_FIELD.length;i++) {
      String fieldName = QUERY_ITEM_FIELD[i];
      Object fieldValue = QUERY_ITEM_VALUE[i];
      QueryItemComparator fieldComp = QUERY_ITEM_COMP[i];
      
      QueryItem item = new QueryItemImpl();
      item.setField(fieldName);
      item.setValue(fieldValue);
      item.setComparator(fieldComp);
      
      testQuery.addQuery(item);
    }
  }

  @BeforeClass
  public static void setUpClass() {
  }

  @AfterClass
  public static void tearDownClass() {
  }

  @Before
  public void setUp() {
    setSubject(new DynamoDBQueryFactory(mockAttributeValue));
    
    initTestQuery();
  }

  @After
  public void tearDown() {
  }

  /**
   * Test that, given basic positive inputs, we get positive outputs.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test basic success");

    Object resultObj = null;
    try {
      resultObj = executePrivateMethod("transformQueryToString",
              new Class[] {Query.class}, new Object[] {testQuery});
    } catch (PrivateTestMethodException e) {
      fail("Unable to run transformQueryToString method.");
    }

    assertNotNull("Is the result null", resultObj);
    assertTrue("Is the result the correct type", resultObj instanceof Map);
    
    Map<String, Object> result = (Map<String, Object>) resultObj;
    Object queryStringObj = result.get("queryString");
    assertEquals("Is the result the correct", "(name = :name) AND (age >= :age)", queryStringObj);
    Object nameObj = result.get("name");
    assertEquals("Is the result value correct", "Smith", nameObj);
    Object ageObj = result.get("age");
    assertEquals("Is the result age value correct", 21, ageObj);
  }
}
