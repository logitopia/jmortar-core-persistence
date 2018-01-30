/**
 * Title: ManualIdModel.java
 * 
 * Author: Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 * Date Created: 23-Sep-2015
 * 
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl.mock;

import com.logitopia.jmortar.core.persistence.annotation.Key;
import com.logitopia.jmortar.core.persistence.dao.annotation.HibernatePersistent;

/**
 * The <tt>ManualIdModel</tt> class is an implementation of <tt>...</tt> that ...(TODO)
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
@HibernatePersistent
public class ManualIdModel {

  /**
   * The ID of the model.
   */
  @Key
  private String id;
  
  /**
   * The name of the model.
   */
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
