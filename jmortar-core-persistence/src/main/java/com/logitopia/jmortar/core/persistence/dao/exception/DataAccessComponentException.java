/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.exception;

/**
 * The <tt>DataAccessComponentException</tt> class is an exception that
 * demonstrates exceptional states in <tt>DataAccessComponents</tt>.
 *
 * @author &lt;Stephen Cheesley stephen.cheesley@gmail.com&gt;
 */
public final class DataAccessComponentException extends Exception {

  /**
   * Block default constructor. We do not want an empty instance of this class.
   */
  private DataAccessComponentException() {
  }

  /**
   * Create an exception with the given message.
   *
   * @param type The component that threw the exception.
   * @param message The description of the exceptional circumstance.
   */
  public DataAccessComponentException(
          final DataAccessComponentExceptionSource type, final String message) {
    super("[" + type.getSource() + "] - " +  message);
  }
}
