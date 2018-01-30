/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory.attributevalue;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/**
 * A handler that is responsible for building an <tt>AttributeValue</tt> for DynamoDB access.
 *
 * @author Stephen Cheesley
 */
public abstract class DynamoDBAttributeValueHandler {

  /**
   * A successor to this handler.
   */
  private DynamoDBAttributeValueHandler successor;

  /**
   * Default Constructor. Create a handler with no successor.
   */
  public DynamoDBAttributeValueHandler() {
  }

  /**
   * Precursor Handler. Create a handler with a successor.
   *
   * @param newSuccessor The handler to be called after this one.
   */
  public DynamoDBAttributeValueHandler(final DynamoDBAttributeValueHandler newSuccessor) {
    successor = newSuccessor;
  }

  /**
   * Is the value applicable to this handler.
   *
   * @return A flag indicating whether or not the value is applicable to this handler.
   */
  protected abstract boolean isValueApplicable(Object value);

  /**
   * Build the <tt>AttributeValue</tt> using the given value.
   *
   * @param value The value to build from.
   * @return The <tt>AttributeValue</tt> to be built.
   */
  protected abstract AttributeValue buildValueImpl(Object value);

  /**
   * Build the dynamo DB compatible value.
   *
   * @param value The value to build into <tt>AttributeValue</tt>.
   * @return The <tt>AttributeValue</tt> that has been built. A null value will be returned if there
   * is no applicable handler.
   */
  public AttributeValue buildValue(Object value) {
    if (isValueApplicable(value)) {
      return buildValueImpl(value);
    } else {
      if (successor != null) {
        return successor.buildValue(value);
      }
    }
    return null;
  }
}
