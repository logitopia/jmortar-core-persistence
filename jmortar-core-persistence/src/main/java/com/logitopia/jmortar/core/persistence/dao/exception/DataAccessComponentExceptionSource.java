/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.exception;

/**
 * The <tt>DataAccessComponentExceptionSource</tt> enum is a type that defines
 * sources for a <tt>DataAccessComponentException</tt>.
 *
 * @author Stephen Cheesley stephen.cheesley@gmail.com
 */
public enum DataAccessComponentExceptionSource {

  /**
   * The source was an instance of <tt>ReadableDataAccessComponent</tt>.
   */
  READABLE("ReadableDataAccessComponent"),
  /**
   * The source was an instance of <tt>WritableDataAccessComponent</tt>.
   */
  WRITABLE("WritableDataAccessComponent");

  /**
   * The source.
   */
  private String source;

  /**
   * Create a new instance of the <tt>DataAccessComponentExceptionSource</tt>.
   *
   * @param newSource The source.
   */
  private DataAccessComponentExceptionSource(final String newSource) {
    source = newSource;
  }

  /**
   * Get the source set in this instance.
   *
   * @return The source.
   */
  public String getSource() {
    return source;
  }
}
