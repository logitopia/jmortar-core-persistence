/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory.attributevalue;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/**
 * A <tt>DynamoDBAttributeValueHandler</tt> that handles numeric values.
 *
 * @author Stephen Cheesley
 */
public class NumberDynamoDBAttributeValueHandler extends DynamoDBAttributeValueHandler {

  /**
   * Default Constructor. Create a new AttributeValue with a successor.
   *
   * @param newSuccessor The successor.
   */
  public NumberDynamoDBAttributeValueHandler(final DynamoDBAttributeValueHandler newSuccessor) {
    super(newSuccessor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isValueApplicable(final Object value) {
    return value instanceof Short
            || value instanceof Long
            || value instanceof Integer
            || value instanceof Float
            || value instanceof Double;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected AttributeValue buildValueImpl(final Object value) {
    return new AttributeValue().withN(value.toString());
  }

}
