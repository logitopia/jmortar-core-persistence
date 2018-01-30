/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.builders;

import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemSortType;
import java.util.List;

/**
 * A <tt>QueryBuilder</tt> that builds a sorted query for just one of its fields.
 *
 * @author Stephen Cheesley
 */
public class SingleSortedQueryBuilder extends BasicQueryBuilder {

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildQueryItems() {
    super.buildQueryItems();

    List<QueryItem> items = query.getQuery();
    for (QueryItem item : items) {
      String field = item.getField();
      switch (field) {
        case "username":
          /* Set sort */
          item.setSortType(QueryItemSortType.DESC);
          break;
      }
    }
  }
}
