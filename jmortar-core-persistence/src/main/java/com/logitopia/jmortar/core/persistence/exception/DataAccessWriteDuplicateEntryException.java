/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.exception;

/**
 * The <tt>DataAccessWriteDuplicateEntryException</tt> class is an implementation of <tt>Exception</tt> that occurs when
 * we attempt to create a duplicate entry.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class DataAccessWriteDuplicateEntryException extends Exception {

  /**
   * Create a <tt>DataAccessWriteDuplicateEntryException</tt> with a message.
   *
   * @param message The message for this exception.
   */
  public DataAccessWriteDuplicateEntryException(String message) {
    super(message);
  }
}
