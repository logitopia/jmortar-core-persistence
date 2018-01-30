/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import java.util.Date;

/**
 * This is a type of report.
 *
 * @author Stephen Cheesley
 */
@DynamoDBDocument
public class ReportType {

  /**
   * The type of report.
   */
  private String type;
  
  /**
   * The value of the type.
   */
  private int typeValue;
  
  /**
   * The timestamp when this type was enabled.
   */
  private Date enabled;

  @DynamoDBAttribute(attributeName = "type")
  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  @DynamoDBAttribute(attributeName = "typeValue")
  public int getTypeValue() {
    return typeValue;
  }

  public void setTypeValue(int typeValue) {
    this.typeValue = typeValue;
  }

  @DynamoDBAttribute(attributeName = "enabled")
  public Date getEnabled() {
    return enabled;
  }

  public void setEnabled(Date enabled) {
    this.enabled = enabled;
  }
  
  
}
