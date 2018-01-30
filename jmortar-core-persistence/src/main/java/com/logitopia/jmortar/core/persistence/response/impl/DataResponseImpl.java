/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.response.impl;

import com.logitopia.jmortar.core.persistence.response.DataResponse;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * The <tt>DataResponseImpl</tt> class is an implementation of <tt>DataResponse</tt> that creates an immutable
 * implementation.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public final class DataResponseImpl implements DataResponse {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DataResponseImpl.class);

  /**
   * The status of the response.
   */
  private byte status;

  /**
   * The attributes of the response.
   */
  private Map<String, Object> attributes;

  /**
   * Default Constructor. Create a response with a status.
   *
   * @param newStatus The status of the response.
   */
  public DataResponseImpl(final byte newStatus) {
    this(newStatus, null);
  }

  /**
   * Extended Constructor. Create a response with a status and attributes.
   *
   * @param newStatus The status of the response.
   * @param newAttributes Attributes of the response.
   */
  public DataResponseImpl(final byte newStatus, final Map<String, Object> newAttributes) {
    status = newStatus;
    attributes = new HashMap<>();

    if (newAttributes != null) {
      attributes = newAttributes;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte getStatus() {
    return status;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getAttribute(final String attributeKey) {
    Object result = null;
    if (attributes != null) {
      result = attributes.get(attributeKey);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAttribute(final String key, final Object value) {
    boolean result = false;

    if (attributes != null) {
      Object existing = attributes.get(key);
      if (existing == null) {
        attributes.put(key, value);
        result = true;
      }
    }

    return result;
  }
}
