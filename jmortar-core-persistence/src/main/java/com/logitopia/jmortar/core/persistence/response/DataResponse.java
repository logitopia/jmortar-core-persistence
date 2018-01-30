/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.response;

import java.util.Map;

/**
 * The <tt>DataResponse</tt> interface is a response to a data request.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public interface DataResponse {

  /**
   * The data request was a success.
   */
  static final byte SUCCESS = 0;

  /**
   * The data request failed because the record was locked.
   */
  static final byte FAIL_LOCKED = 1;

  /**
   * The data request failed because the record was a duplicate of an existing record.
   */
  static final byte FAIL_DUPLICATE = 2;
  
  /**
   * The data request failed for a non-specific reason.
   */
  static final byte FAIL_GENERAL = 3;

  /**
   * Get the status of the request.
   *
   * @return The status.
   */
  byte getStatus();

  /**
   * Get the attributes for the response.
   *
   * @return The attributes.
   */
  Map<String, Object> getAttributes();

  /**
   * Get the attribute for the key.
   *
   * @param attributeKey The attribute key.
   * @return The attribute.
   */
  Object getAttribute(String attributeKey);

  /**
   * Add an attribute to the data response.
   *
   * @param key The identifying key for the new attribute.
   * @param value The value to add as an attribute.
   * 
   * @return Success flag.
   */
  boolean addAttribute(String key, Object value);
}
