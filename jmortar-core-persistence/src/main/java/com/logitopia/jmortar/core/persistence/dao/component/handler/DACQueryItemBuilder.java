/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.handler;

import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import java.util.List;
import java.util.Map;

/**
 * The <tt>DACQueryItemBuilder</tt> interface is a handler that can build a <tt>QueryItem</tt> for a data access
 * components.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public interface DACQueryItemBuilder {

  /**
   * Generate a list of <tt>QueryItem</tt> based on the key items supplied.
   *
   * @param keyItems The key items to build query items from.
   * @return A list of <tt>QueryItem</tt> that make up the key query.
   */
  List<QueryItem> buildItems(Map<String, Object> keyItems);
}
