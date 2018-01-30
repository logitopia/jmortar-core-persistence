/**
 * Title: TestReadableDataAccessComponent.java
 *
 * Author: Stephen Cheesley stephen.cheesley@gmail.com Date Created: 29-Jun-2015
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.logitopia.jmortar.core.persistence.dao.component.impl.call.FindCall;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.mock.Shoe;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The <tt>TestReadableDataAccessComponent</tt> class is an implementation that ...(TODO)
 *
 * @author &lt;Stephen Cheesley stephen.cheesley@gmail.com&gt;
 */
public class TestReadableDataAccessComponent
        extends AbstractReadableDataAccessComponent<Shoe, Object, Object, Object, MockResource> {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(TestReadableDataAccessComponent.class);

  /**
   * Calls to the <tt>find</tt> method.
   */
  private List<FindCall> findCalls;

  /**
   * The value to be returned from the find call.
   */
  private List<Shoe> findReturn;

  public TestReadableDataAccessComponent(final QueryFactory<Object, Object, Object> queryFactory) {
    super(queryFactory, new MockResource());
    findCalls = new LinkedList<>();
  }

  /**
   * Get a list of the calls made to the <tt>find</tt> method.
   *
   * @return
   */
  public List<FindCall> getFindCalls() {
    return findCalls;
  }

  /**
   * Get the <tt>find</tt> method return value.
   *
   * @return The return value.
   */
  public List<Shoe> getFindReturn() {
    return findReturn;
  }

  /**
   * Set the <tt>find</tt> method return value.
   *
   * @param newFindReturn The return value.
   */
  public void setFindReturn(final List<Shoe> newFindReturn) {
    findReturn = newFindReturn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getKeyFields() {
    List<String> result = new ArrayList<>();

    result.add("make");
    result.add("model");

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List getAll(Class newClazz) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Shoe> getAll(Class newClazz, int pageNumber, int pageSize) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List find(Class newClazz, Query findFilter) {
    FindCall findCall = new FindCall(newClazz, findFilter);
    findCalls.add(findCall);

    return findReturn;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Shoe getEntityByID(Class newClazz, long id) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Reset all calls made to this subject.
   */
  public void reset() {
    findCalls.clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Shoe> find(Class newClazz, Query findFilter, int pageNumber, int pageSize) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
