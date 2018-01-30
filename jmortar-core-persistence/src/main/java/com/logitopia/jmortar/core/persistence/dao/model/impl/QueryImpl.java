/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.model.impl;

import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.object.util.impl.HashCodeUtil;
import com.logitopia.jmortar.core.persistence.dao.model.QueryModel;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryLogicalConjunction;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The class <tt>QueryImpl</tt> represents an implementation of a
 * <tt>Query</tt>.
 * 
 * @param <T> The type that the query holds (instance of <tt>QueryModel</tt>.
 */
public final class QueryImpl<T extends QueryModel> implements Query<T> {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(QueryImpl.class);

  /**
   * The type of query (AND, OR, e.t.c.).
   */
  private QueryLogicalConjunction type;

  /**
   * The items that comprise this query (ordered list).
   */
  private List<T> items;

  /**
   * Specifies whether this operation is negated.
   */
  private boolean not;

  /**
   * Default Constructor.
   */
  public QueryImpl() {
    items = new ArrayList<>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public QueryLogicalConjunction getQueryType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setQueryType(final QueryLogicalConjunction newQueryType) {
    type = newQueryType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> getQuery() {
    return items;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setQuery(final List<T> newQuery) {
    items = newQuery;
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
  public void addQuery(final T newItem) {
    if (items == null) {
      items = new ArrayList<>();
    }

    if (newItem != null) {
      items.add(newItem);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeQuery(final T oldItem) {
    if (items != null && oldItem != null) {
      items.remove(oldItem);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int hash = HashCodeUtil.SEED;
    hash = HashCodeUtil.hash(hash, type);
    hash = HashCodeUtil.hash(hash, items);
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
      final QueryImpl that = (QueryImpl) obj;

      if (type != null && type.equals(that.type)
              && (items != null && items.equals(that.items))
              && Boolean.compare(not, that.not) == 0) {
        result = true;
      }
    }

    return result;
  }
}
