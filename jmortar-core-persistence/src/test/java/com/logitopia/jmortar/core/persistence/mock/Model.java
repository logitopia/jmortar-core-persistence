/**
 * Title: model.java
 *
 * Author: Stephen Cheesley stephen.cheesley@gmail.com Date Created: 22-Aug-2015
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.mock;

/**
 * The <tt>Model</tt> interface is a ...TODO
 *
 * @author &lt;Stephen Cheesley stephen.cheesley@gmail.com&gt;
 */
public interface Model {

  /**
   * Get name.
   *
   * @return The name.
   */
  String getName();

  /**
   * Set name.
   *
   * @param newName The name.
   */
  void setName(String newName);

  /**
   * Get description.
   *
   * @return The description.
   */
  String getDescription();

  /**
   * Set description.
   *
   * @param newDescription The description.
   */
  void setDescription(String newDescription);
}
