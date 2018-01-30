/**
 * Title: TestWritableDataAccessComponent.java
 *
 * Author: Stephen Cheesley stephen.cheesley@gmail.com Date Created: 05-Jul-2015
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import org.apache.log4j.Logger;

/**
 * The <tt>TestWritableDataAccessComponent</tt> class is an implementation that
 * ...(TODO)
 *
 * @author &lt;Stephen Cheesley stephen.cheesley@gmail.com&gt;
 */
public class TestWritableDataAccessComponent implements WritableDataAccessComponent {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(TestWritableDataAccessComponent.class);

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(Object newModel) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(Object newModel) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Object newModel) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
