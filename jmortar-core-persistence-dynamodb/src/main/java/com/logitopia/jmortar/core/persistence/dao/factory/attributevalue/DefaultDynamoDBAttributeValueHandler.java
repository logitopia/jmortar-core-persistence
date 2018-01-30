/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory.attributevalue;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/**
 * A <tt>DynamoDBAttributeValueHandler</tt> that is a catch-all for anything that is not picked up
 * by the other handlers.
 *
 * @author Stephen Cheesley
 */
public class DefaultDynamoDBAttributeValueHandler extends DynamoDBAttributeValueHandler {

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isValueApplicable(final Object value) {
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AttributeValue buildValueImpl(final Object value) {
    return new AttributeValue().withS(value.toString());
  }

}
