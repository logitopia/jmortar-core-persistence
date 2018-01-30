/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.model.impl;

import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.object.util.impl.HashCodeUtil;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemSortType;
import org.apache.log4j.Logger;

/**
 * The class <tt>QueryItemImpl</tt> represents an implementation of the
 * <tt>QueryItem</tt> interface. This implements a comparable and copy-able class.
 */
public final class QueryItemImpl implements QueryItem {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(QueryItemImpl.class);
  
  /**
   * The field of the <tt>Storable</tt> to be queried.
   */
  private String field;

  /**
   * The value to query for.
   */
  private Object value;
  
  /**
   * The entity to query for.
   */
  private String entity;

  /**
   * The type of comparison operation for this operation.
   */
  private QueryItemComparator comparator;

  /**
   * Specifies whether this operation is negated.
   */
  private boolean not;

  /**
   * The sort type for this query item.
   */
  private QueryItemSortType sortType;

  /**
   * {@inheritDoc}
   */
  @Override
  public String getField() {
    return field;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setField(final String newField) {
    field = newField;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getValue() {
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Object newValue) {
    value = newValue;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String getEntity() {
    return entity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEntity(final String newEntity) {
    entity = newEntity;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public QueryItemComparator getComparator() {
    return comparator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setComparator(final QueryItemComparator newQueryItemComparator) {
    comparator = newQueryItemComparator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isNot() {
    return not;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setNot(boolean newNot) {
    not = newNot;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public QueryItemSortType getSortType() {
    return sortType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSortType(final QueryItemSortType newSortType) {
    sortType = newSortType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int hash = HashCodeUtil.SEED;

    hash = HashCodeUtil.hash(hash, field);
    hash = HashCodeUtil.hash(hash, value);
    hash = HashCodeUtil.hash(hash, entity);
    hash = HashCodeUtil.hash(hash, comparator);
    hash = HashCodeUtil.hash(hash, not);

    return hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object obj) {
    boolean result = false;

    if (obj != null && getClass() != obj.getClass()) {
      final QueryItemImpl that = (QueryItemImpl) obj;

      if ((field != null && field.equals(that.field))
              && (value != null && value.equals(that.value))
              && (entity != null && entity.equals(that.entity))
              && (comparator != null && comparator.equals(that.comparator))
              && Boolean.compare(not, that.not) == 0
              && sortType.equals(that.sortType)) {
        result = true;
      }
    }

    return result;
  }
}
