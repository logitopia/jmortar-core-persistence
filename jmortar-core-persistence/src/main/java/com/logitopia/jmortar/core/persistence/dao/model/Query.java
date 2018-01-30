/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.model;

import com.logitopia.jmortar.core.persistence.dao.model.type.QueryLogicalConjunction;
import java.util.List;

/**
 * The <code>Query</code> interface represents a data query and is a type of <tt>QueryModel</tt>.
 * This query can be applied to a DAO.
 *
 * @author s.cheesley
 * @param <T> The type that the query holds (instance of <tt>QueryModel</tt>.
 */
public interface Query<T extends QueryModel> extends QueryModel {

  /**
   * Get the type of query.
   *
   * @return The type of query to set.
   */
  QueryLogicalConjunction getQueryType();

  /**
   * Set the type of query.
   *
   * @param newQueryType The type of query to set.
   */
  void setQueryType(QueryLogicalConjunction newQueryType);

  /**
   * Is this a negated query (logically inverted operation).
   *
   * @return boolean - True indicates that this is a not operation.
   */
  boolean isNot();

  /**
   * Set whether this is a negated operation.
   *
   * @param newNot Indicating a negated operation (true = NOT);
   */
  void setNot(boolean newNot);

  /**
   * Get the list of query items. The list of query items combined with the type comprise the query.
   *
   * @return List A list of all query items that comprise this query.
   */
  List<T> getQuery();

  /**
   * Set the list of query items. The list of query items combined with the type comprise the query.
   *
   * @param newQuery A list of query items that comprise this query.
   */
  void setQuery(List<T> newQuery);

  /**
   * Add a query item to the query.
   *
   * @param newItem The item to be added to the query.
   */
  void addQuery(T newItem);

  /**
   * Remove a query item to the query.
   *
   * @param oldItem The item to be removed from the query.
   */
  void removeQuery(T oldItem);
}
