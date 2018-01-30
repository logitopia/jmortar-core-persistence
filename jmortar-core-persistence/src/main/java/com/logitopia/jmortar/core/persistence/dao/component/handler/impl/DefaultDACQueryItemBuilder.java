/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.handler.impl;

import com.logitopia.jmortar.core.persistence.dao.component.handler.DACQueryItemBuilder;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryItemImpl;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>DefaultDACQueryItemBuilder</tt> class is an implementation of <tt>DACQueryItemBuilder</tt> that executes the
 * default query item build.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public final class DefaultDACQueryItemBuilder implements DACQueryItemBuilder {

  /**
   * {@inheritDoc}
   */
  @Override
  public List<QueryItem> buildItems(final Map<String, Object> keyItems) {
    List<QueryItem> result = new ArrayList<>();

    for (Map.Entry<String, Object> item : keyItems.entrySet()) {
      QueryItem queryItem = new QueryItemImpl();
      queryItem.setField(item.getKey());
      queryItem.setValue(item.getValue());
      queryItem.setComparator(QueryItemComparator.EQUALS);
      result.add(queryItem);
    }

    return result;
  }
}
