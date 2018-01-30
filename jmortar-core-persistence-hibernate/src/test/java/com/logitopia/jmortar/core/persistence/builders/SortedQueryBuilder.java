/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.builders;

import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemSortType;
import java.util.List;

/**
 * A <tt>QueryBuilder</tt> that builds a sorted query.
 *
 * @author Stephen Cheesley
 */
public class SortedQueryBuilder extends BasicQueryBuilder{

  /**
   * {@inheritDoc}
   */
  @Override
  public void buildQueryItems() {
    super.buildQueryItems();
    
    List<QueryItem> items = query.getQuery();
    for (QueryItem item: items) {
      String field = item.getField();
      switch(field) {
        case "username":
          /* Set sort */
          item.setSortType(QueryItemSortType.UNSPECIFIED);
          break;
        case "firstName":
          item.setSortType(QueryItemSortType.ASC);
          break;
        case "password":
          item.setSortType(QueryItemSortType.DESC);
          break;
        case "attempts":
          /* No sort set */
          break;
      }
    }
  }
}
