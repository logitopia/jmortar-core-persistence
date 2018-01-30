/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.model;

import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemSortType;

/**
 * The <code>QueryItem</code> interface represents a data query and is a type of
 * <tt>QueryModel</tt>. This query can be applied to a storable object in order to supply more
 * granular data.
 *
 * @author s.cheesley
 */
public interface QueryItem extends QueryModel {

  /**
   * Get the field that this query applies to.
   *
   * @return String Field name to be queried.
   */
  String getField();

  /**
   * Set the field that this query will be applied to.
   *
   * @param newField Field name to be queried
   */
  void setField(String newField);

  /**
   * Get the value to be queried for.
   *
   * @return Object The complex type to be queried.
   */
  Object getValue();

  /**
   * Set the value to be queried for.
   *
   * @param newValue The complex type to be queried.
   */
  void setValue(Object newValue);

  /**
   * Set the entity to be queried for.
   *
   * @return T The entity.
   */
  String getEntity();

  /**
   * Set the entity to be queried for.
   *
   * @param newEntity The entity to be queried for.
   */
  void setEntity(String newEntity);

  /**
   * Get the type of comparison operation that this item represents.
   *
   * @return String The comparison operation defined by this item.
   */
  QueryItemComparator getComparator();

  /**
   * Set the type of comparison operation that this item represents.
   *
   * @param newQueryItemComparator The comparison operation defined by this item.
   */
  void setComparator(QueryItemComparator newQueryItemComparator);

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
   * Get the sort type for the field referenced by this query item.
   *
   * @return The <tt>QueryItemSortType</tt>.
   */
  QueryItemSortType getSortType();

  /**
   * Set the sort type for the field referenced by this query item.
   *
   * @param newSortType The <tt>QueryItemSortType</tt>.
   */
  void setSortType(QueryItemSortType newSortType);
}
