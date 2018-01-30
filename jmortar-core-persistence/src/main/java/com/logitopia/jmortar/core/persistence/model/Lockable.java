/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.model;

/**
 * The <tt>Lockable</tt> interface represents a model object that implements locking when persisted.
 *
 * @author &lt;Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;&gt;
 */
public interface Lockable {

  /**
   * Get the version of the object that was retrieved from the database.
   *
   * @return The version.
   */
  long getVersion();
}
