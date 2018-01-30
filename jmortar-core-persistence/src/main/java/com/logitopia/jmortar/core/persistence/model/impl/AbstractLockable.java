/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.model.impl;

import com.logitopia.jmortar.core.persistence.model.Lockable;

/**
 * The <tt>AbstractLockable</tt> class is an abstract implementation of <tt>Lockable</tt> that allows models to be
 * locked.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public abstract class AbstractLockable implements Lockable {

  /**
   * The persisted object version.
   */
  private long version;

  /**
   * {@inheritDoc}
   */
  @Override
  public long getVersion() {
    return version;
  }

  /**
   * Set the version of the lockable.
   *
   * @param newVersion The version number.
   */
  public void setVersion(final long newVersion) {
    version = newVersion;
  }
}
