/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.exception;

/**
 * The <tt>DataAccessWriteLockingException</tt> class is an implementation of <tt>Exception</tt> that occurs when a
 * write has failed because the record is locked.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class DataAccessWriteLockingException extends Exception {

  /**
   * Create a <tt>DataAccessWriteLockingException</tt> with a message.
   *
   * @param message The message for this exception.
   */
  public DataAccessWriteLockingException(final String message) {
    super(message);
  }
}
