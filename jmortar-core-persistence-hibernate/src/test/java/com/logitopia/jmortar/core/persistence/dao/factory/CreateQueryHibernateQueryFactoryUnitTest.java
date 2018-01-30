/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory;

import com.logitopia.jmortar.core.persistence.builders.BasicQueryBuilder;
import com.logitopia.jmortar.core.persistence.builders.QueryBuilder;
import com.logitopia.jmortar.core.persistence.builders.QueryDirector;
import com.logitopia.jmortar.core.persistence.builders.SingleSortedQueryBuilder;
import com.logitopia.jmortar.core.persistence.builders.SortedQueryBuilder;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryFactoryRequest;
import com.logitopia.jmortar.core.test.AbstractUnitTest;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit testing of the <tt>createQuery</tt> unit on the <tt>HibernateQueryFactory</tt>.
 *
 * @author Stephen Cheesley
 */
public class CreateQueryHibernateQueryFactoryUnitTest
        extends AbstractUnitTest<HibernateQueryFactory> {
  
  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(CreateQueryHibernateQueryFactoryUnitTest.class);

  /**
   * The director that build the query.
   */
  private QueryDirector queryDirector;
  
  /**
   * The builder that builds the query.
   */
  private QueryBuilder queryBuilder;
  
  /**
   * A mock hibernate session that will create the hibernate query.
   */
  private Session mockSession;
  
  /**
   * A mock hibernate query to test with.
   */
  private org.hibernate.Query mockHibernateQuery;
  
  /**
   * The test entity to build the query for.
   */
  private Class testEntity;
  
  /**
   * Test query factory request.
   */
  private QueryFactoryRequest<Class, Session> testQFRequest;
  
  /**
   * Default Constructor.
   */
  public CreateQueryHibernateQueryFactoryUnitTest() {
  }
  
  /**
   * Initialise the query builder.
   */
  private void initQueryBuilder() {
    queryDirector = new QueryDirector();
    queryBuilder = new BasicQueryBuilder();
  }
  
  /**
   * Initialise the test entity.
   */
  private void initTestEntity() {
    testEntity = Object.class;
  }
  
  /**
   * Initialise the test hibernate query
   */
  private void initTestQuery() {
    mockHibernateQuery = mock(org.hibernate.Query.class);
  }
  
  /**
   * Initialise the mock session.
   */
  private void initMockSession() {
    initTestQuery();
    mockSession = mock(Session.class);
    
    when(mockSession.createQuery(any(String.class))).thenReturn(mockHibernateQuery);
  }
  
  /**
   * Initialise the test query factory request.
   */
  private void initTestQFRequest() {
    initMockSession();
    initTestEntity();
    
    testQFRequest = new QueryFactoryRequest<>(testEntity, mockSession);
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
    initQueryBuilder();
    initTestQFRequest();
    
    setSubject(new HibernateQueryFactory());
  }

  /**
   * {@inheritDoc}
   */
  @After
  public void tearDown() {
  }
  
  /**
   * Test that, given a basic positive input, we get a basic positive output.
   */
  @Test
  public void testBasicSuccess() {
    LOG.info("Test basic success");
    
    Query query = queryDirector.buildQuery(queryBuilder);
    
    Object result = getSubject().createQuery(query, testQFRequest);
    
    assertNotNull("Is the response null", result);
    assertTrue("Is the result of the correct type", result instanceof org.hibernate.Query);
    
    /* Validate the query string */
    ArgumentCaptor<String> queryString = ArgumentCaptor.forClass(String.class);
    verify(mockSession, times(1)).createQuery(queryString.capture());
    
    String queryStringVal = queryString.getValue();
    assertNotNull("Is query string null", queryStringVal);
    assertEquals("Is the query string value as expected",
            "from java.lang.Object WHERE (username = :username) AND (firstName = :firstName) AND (password = :password) AND (attempts <= :attempts)",
            queryStringVal);
    
    /* Validate the parameters */
    ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Object> parameterCaptor = ArgumentCaptor.forClass(Object.class);
    verify(mockHibernateQuery, times(4)).setParameter(keyCaptor.capture(), parameterCaptor.capture());
    
    checkParameterValues(keyCaptor, parameterCaptor);
  }
  
  /**
   * Test that, given a basic sorted positive input, we get a basic positive output.
   */
  @Test
  public void testSortSuccess() {
    LOG.info("Test sort success");
    
    Query query = queryDirector.buildQuery(new SortedQueryBuilder());
    
    Object result = getSubject().createQuery(query, testQFRequest);
    
    assertNotNull("Is the response null", result);
    assertTrue("Is the result of the correct type", result instanceof org.hibernate.Query);
    
    /* Validate the query string */
    ArgumentCaptor<String> queryString = ArgumentCaptor.forClass(String.class);
    verify(mockSession, times(1)).createQuery(queryString.capture());
    
    String queryStringVal = queryString.getValue();
    assertNotNull("Is query string null", queryStringVal);
    assertEquals("Is the query string value as expected",
            "from java.lang.Object WHERE (username = :username) AND (firstName = :firstName) AND (password = :password) AND (attempts <= :attempts) ORDER BY username, firstName asc, password desc",
            queryStringVal);
    
    /* Validate the parameters */
    ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Object> parameterCaptor = ArgumentCaptor.forClass(Object.class);
    verify(mockHibernateQuery, times(4)).setParameter(keyCaptor.capture(), parameterCaptor.capture());
    
    checkParameterValues(keyCaptor, parameterCaptor);
  }
  
  /**
   * Test that, given a simple (one field) sort positive input, we get a basic positive output.
   */
  @Test
  public void testSimpleSortSuccess() {
    LOG.info("Test simple sort success");
    
    Query query = queryDirector.buildQuery(new SingleSortedQueryBuilder());
    
    Object result = getSubject().createQuery(query, testQFRequest);
    
    assertNotNull("Is the response null", result);
    assertTrue("Is the result of the correct type", result instanceof org.hibernate.Query);
    
    /* Validate the query string */
    ArgumentCaptor<String> queryString = ArgumentCaptor.forClass(String.class);
    verify(mockSession, times(1)).createQuery(queryString.capture());
    
    String queryStringVal = queryString.getValue();
    assertNotNull("Is query string null", queryStringVal);
    assertEquals("Is the query string value as expected",
            "from java.lang.Object WHERE (username = :username) AND (firstName = :firstName) AND (password = :password) AND (attempts <= :attempts) ORDER BY username desc",
            queryStringVal);
    
    /* Validate the parameters */
    ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Object> parameterCaptor = ArgumentCaptor.forClass(Object.class);
    verify(mockHibernateQuery, times(4)).setParameter(keyCaptor.capture(), parameterCaptor.capture());
    
    checkParameterValues(keyCaptor, parameterCaptor);
  }
  
  /**
   * Check the parameter values as captured from mocks.
   * @param keyCaptor
   * @param parameterCaptor 
   */
  private void checkParameterValues(final ArgumentCaptor<String> keyCaptor,
          final ArgumentCaptor<Object> parameterCaptor) {
    List<String> keys = keyCaptor.getAllValues();
    List<Object> params = parameterCaptor.getAllValues();
    
    for (int i=0; i < keys.size(); i++) {
      String key = keys.get(i);
      Object param = params.get(i);
      
      assertNotNull("Is param null", param);
      switch(key) {
        case "username":
          assertTrue("Is param correct type", param instanceof String);
          String username = (String) param;
          assertEquals("Is username value correct", "bobtb", username);
          break;
        case "firstName":
          assertTrue("Is param correct type", param instanceof String);
          String firstName = (String) param;
          assertEquals("Is first name value correct", "bob", firstName);
          break;
        case "password":
          assertTrue("Is param correct type", param instanceof String);
          String password = (String) param;
          assertEquals("Is password value correct", "thebuilder", password);
          break;
        case "attempts":
          assertTrue("Is param correct type", param instanceof Integer);
          Integer attempts = (Integer) param;
          assertEquals("Is attempts value correct", new Integer(4), attempts);
          break;
      }
    }
  }
}
