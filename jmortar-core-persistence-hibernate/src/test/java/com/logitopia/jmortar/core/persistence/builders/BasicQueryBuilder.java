/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.builders;

import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryImpl;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryItemImpl;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryLogicalConjunction;

/**
 * A <tt>QueryBuilder</tt> that builds a standard query for testing.
 *
 * @author Stephen Cheesley
 */
public class BasicQueryBuilder extends QueryBuilder<QueryItem> {

  /**
   * Test field names for the query.
   */
  public static final String[] FIELD_NAME = {"username", "firstName", "password", "attempts"};

  /**
   * Test values for the query.
   */
  public static final Object[] VALUE = {"bobtb", "bob", "thebuilder", 4};

  /**
   * Test query item comparators.
   */
  public static final QueryItemComparator[] COMPARATORS = {QueryItemComparator.EQUALS,
    QueryItemComparator.EQUALS,
    QueryItemComparator.EQUALS,
    QueryItemComparator.LESS_EQ};

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildQueryInstance() {
    query = new QueryImpl();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildQueryItems() {
    for (int i=0; i < FIELD_NAME.length; i++) {
      /* Add the first query item.. */
      QueryItem item = new QueryItemImpl();
      item.setField(FIELD_NAME[i]);
      item.setValue(VALUE[i]);
      item.setComparator(COMPARATORS[i]);

      query.addQuery(item);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildMainQueryDetail() {
    query.setQueryType(QueryLogicalConjunction.AND);
  }
}
