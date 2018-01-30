/**
 * Title: MockModel.java
 *
 * Author: Stephen Cheesley stephen.cheesley@gmail.com Date Created: 22-Aug-2015
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.mock.impl;

import com.logitopia.jmortar.core.persistence.mock.Model;
import java.util.Objects;
import org.apache.log4j.Logger;

/**
 * The <tt>MockModel</tt> class is an implementation that ...(TODO)
 *
 * @author &lt;Stephen Cheesley stephen.cheesley@gmail.com&gt;
 */
public class MockModel implements Model {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(MockModel.class);

  /**
   * The name.
   */
  private String name;

  /**
   * The description.
   */
  private String description;

  /**
   * {@inheritDoc}
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setName(final String newName) {
    name = newName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getDescription() {
    return description;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDescription(final String newDescription) {
    description = newDescription;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int hash = 5;
    hash = 43 * hash + Objects.hashCode(this.name);
    hash = 43 * hash + Objects.hashCode(this.description);
    return hash;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final MockModel other = (MockModel) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return true;
  }
  
  
}
