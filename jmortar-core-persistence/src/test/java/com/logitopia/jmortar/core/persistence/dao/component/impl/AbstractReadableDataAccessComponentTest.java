/**
 * Title: AbstractReadableDataAccessComponentTest.java
 *
 * Author: Stephen Cheesley <stephen.cheesley@gmail.com>
 * Date Created: 29-Jun-2015
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.logitopia.jmortar.core.persistence.dao.component.impl.call.FindCall;
import com.logitopia.jmortar.core.persistence.dao.exception.DataAccessComponentException;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import com.logitopia.jmortar.core.persistence.mock.Shoe;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;

/**
 * The <tt>AbstractReadableDataAccessComponentTest</tt> unit test class is a
 * unit test of the <tt>...</tt> module.
 *
 * @author &lt;Stephen Cheesley stephen.cheesley@gmail.com&gt;
 */
public class AbstractReadableDataAccessComponentTest {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(AbstractReadableDataAccessComponentTest.class);

  /**
   * The default shoe make.
   */
  private static final String DEFAULT_SHOE_MAKE = "Nike";

  /**
   * The default shoe model.
   */
  private static final String DEFAULT_SHOE_MODEL = "Air";

  /**
   * The default shoe material.
   */
  private static final String DEFAULT_SHOE_MATERIAL = "Cloth";

  /**
   * The default shoe colour.
   */
  private static final String DEFAULT_SHOE_COLOUR = "Red";

  /**
   * The test subject.
   */
  private TestReadableDataAccessComponent subject;

  /**
   * Default Constructor.
   */
  public AbstractReadableDataAccessComponentTest() {
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
    subject = new TestReadableDataAccessComponent(mock(QueryFactory.class));
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }

  /**
   * Calibrate the test data for the <tt>testGetEntityByID</tt>.
   */
  private void calibrateTestGetEntityByID() {
    Shoe basicShoe = new Shoe();
    basicShoe.setMake(DEFAULT_SHOE_MAKE);
    basicShoe.setModel(DEFAULT_SHOE_MODEL);
    basicShoe.setMaterial(DEFAULT_SHOE_MATERIAL);
    basicShoe.setColour(DEFAULT_SHOE_COLOUR);

    List<Shoe> shoes = new ArrayList<Shoe>();
    shoes.add(basicShoe);

    subject.setFindReturn(shoes);
    subject.reset();
  }

  /**
   * Run assertions for the test of the <tt>getEntityByID</tt> method.
   */
  private void assertFindReturnGetEntityByID() {
    List<FindCall> calls = subject.getFindCalls();

    assertNotNull("find calls null test", calls);
    assertEquals("find calls size test", 1, calls.size());

    /* Get the call made */
    FindCall call = calls.get(0);
    assertNotNull("find call null test", call);
    assertEquals("find call class test", Shoe.class, call.getClazz());

    Query callQuery = call.getQuery();
    assertNotNull("call query null test", callQuery);
    List<QueryItem> queryItems = callQuery.getQuery();
    assertNotNull("query items null test", queryItems);
    assertEquals("query items size test", 2, queryItems.size());

    for (QueryItem item : queryItems) {
      switch (item.getField()) {
        case "make":
          /* do something */
          assertEquals("item value test", DEFAULT_SHOE_MAKE, item.getValue());
          assertEquals("item type test", QueryItemComparator.EQUALS, item.getComparator());
          assertFalse("item negation test", item.isNot());
          break;
        case "model":
          /* do something */
          assertEquals("item value test", DEFAULT_SHOE_MODEL, item.getValue());
          assertEquals("item type test", QueryItemComparator.EQUALS, item.getComparator());
          assertFalse("item negation test", item.isNot());
          break;
        default:
          /* An unexpected query item has got in. Fail and report. */
          LOG.info("Unexpected query item [" + item.getField() + "]");
          fail("Unexpected query item found.");
          break;
      }
    }
  }

  /**
   * Test that we can call <tt>getEntityByID</tt> with expected input and get
   * expected successful output.
   */
  @Test
  public void testGetEntityByID() throws DataAccessComponentException {
    LOG.info("testGetEntityByID");

    calibrateTestGetEntityByID();

    Shoe basicShoe = new Shoe();
    basicShoe.setMake(DEFAULT_SHOE_MAKE);
    basicShoe.setModel(DEFAULT_SHOE_MODEL);

    Shoe result = subject.getEntityByID(Shoe.class, basicShoe);

    assertNotNull("Result null test", result);
    /* Assert that the correct entity has been retrieved. */
    assertEquals("Result cloth value test", DEFAULT_SHOE_MATERIAL,
            result.getMaterial());
    assertEquals("Result colour value test", DEFAULT_SHOE_COLOUR,
            result.getColour());

    /* Test that the appropriate call was made to the find method. */
    assertFindReturnGetEntityByID();
  }

  /**
   * Calibrate the test data for the <tt>testGetEntityByID</tt>.
   */
  private void calibrateTestGetEntityByIDWIthNoEntities() {
    List<Shoe> shoes = new ArrayList<Shoe>();

    subject.setFindReturn(shoes);
  }
  
  /**
   * Run assertions for the test of the <tt>getEntityByID</tt> method.
   */
  private void assertFindReturnGetEntityByIDWithNoEntities() {
    List<FindCall> calls = subject.getFindCalls();

    assertNotNull("find calls null test", calls);
    assertEquals("find calls size test", 1, calls.size());
  }

  /**
   * Test that, when we call <tt>getEntityByID</tt> with expected (negative)
   * input, we get expected negative output.
   */
  @Test
  public void testGetEntityByIDWithNoEntities() {
    LOG.info("testGetEntityByIDWIthNoEntities");

    String expectedExceptionMessage = "[ReadableDataAccessComponent] - Unable "
            + "to find entity for Query [(make = 'Nike') AND (model = 'Air')]";

    calibrateTestGetEntityByIDWIthNoEntities();

    Shoe basicShoe = new Shoe();
    basicShoe.setMake(DEFAULT_SHOE_MAKE);
    basicShoe.setModel(DEFAULT_SHOE_MODEL);

    Shoe result = null;
    try {
      result = subject.getEntityByID(Shoe.class, basicShoe);
    } catch (DataAccessComponentException ex) {
      LOG.info("Data Access Component Exception was thrown.");
      assertNotNull("Test exception message", expectedExceptionMessage);
    }
    assertNull("Result null test", result);
    
    assertFindReturnGetEntityByIDWithNoEntities();
  }
  
  /**
   * Calibrate the test data for the <tt>testGetEntityByID</tt>.
   */
  private void calibrateTestGetEntityByIDWithTooManyEntities() {
    Shoe basicShoe = new Shoe();
    basicShoe.setMake(DEFAULT_SHOE_MAKE);
    basicShoe.setModel(DEFAULT_SHOE_MODEL);
    basicShoe.setMaterial(DEFAULT_SHOE_MATERIAL);
    basicShoe.setColour(DEFAULT_SHOE_COLOUR);
    
    Shoe extraShoe = new Shoe();
    extraShoe.setMake("ExtraMake");
    extraShoe.setModel("ExtraModel");
    extraShoe.setMaterial("ExtraMaterial");
    extraShoe.setColour("ExtraColor");

    List<Shoe> shoes = new ArrayList<Shoe>();
    shoes.add(basicShoe);
    shoes.add(extraShoe);

    subject.setFindReturn(shoes);
  }
  
  /**
   * Test that, when we call <tt>getEntityByID</tt> with expected (negative)
   * input, we get expected negative output.
   */
  @Test
  public void testGetEntityByIDWithTooManyEntities() {
    LOG.info("testGetEntityByIDWithTooManyEntities");

    String expectedExceptionMessage = "[ReadableDataAccessComponent] - Too many"
            + " entities returned for Query [(make = 'Nike') AND (model ="
            + " 'Air')]";

    calibrateTestGetEntityByIDWithTooManyEntities();

    Shoe basicShoe = new Shoe();
    basicShoe.setMake(DEFAULT_SHOE_MAKE);
    basicShoe.setModel(DEFAULT_SHOE_MODEL);

    Shoe result = null;
    try {
      result = subject.getEntityByID(Shoe.class, basicShoe);
    } catch (DataAccessComponentException ex) {
      LOG.info("Data Access Component Exception was thrown.");
      assertNotNull("Test exception message", expectedExceptionMessage);
    }
    assertNull("Result null test", result);
    
    assertFindReturnGetEntityByIDWithNoEntities();
  }

}
