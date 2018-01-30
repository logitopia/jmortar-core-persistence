/**
 * Title: FindCall.java
 *
 * Author: Stephen Cheesley stephen.cheesley@gmail.com Date Created: 02-Jul-2015
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl.call;

import com.logitopia.jmortar.core.persistence.dao.model.Query;
import org.apache.log4j.Logger;

/**
 * The <tt>FindCall</tt> class is an implementation that contains the data from
 * the call made to the <tt>find</tt> method on the
 * <tt>ReadableDataAccessComponent</tt>.
 *
 * @author &lt;Stephen Cheesley stephen.cheesley@gmail.com&gt;
 */
public final class FindCall {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(FindCall.class);

  /**
   * The class passed as part of the call.
   */
  private Class clazz;

  /**
   * The query passed as part of the call.
   */
  private Query query;

  /**
   * Control the empty constructor.
   */
  private FindCall() {
  }

  /**
   * Default Constructor.
   *
   * @param newClazz The class that was passed to the find.
   * @param newQuery The query that was passed to the find.
   */
  public FindCall(final Class newClazz, final Query newQuery) {
    clazz = newClazz;
    query = newQuery;
  }

  /**
   * Get the class that was passed to the find method.
   *
   * @return The class that was passed.
   */
  public Class getClazz() {
    return clazz;
  }

  /**
   * Get the query that was passed to the find method.
   *
   * @return The query that was passed.
   */
  public Query getQuery() {
    return query;
  }
}
