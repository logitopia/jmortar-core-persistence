/*
 * Title: Shoe
 *
 * Author: s.cheesley @ Logitopia Technologies
 * Date Created: Jan 18, 2014
 *
 * This code is the intelectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl.mock;

import com.logitopia.jmortar.core.persistence.annotation.Key;
import com.logitopia.jmortar.core.persistence.dao.annotation.HibernatePersistent;
import com.logitopia.jmortar.core.persistence.model.impl.AbstractLockable;
import org.apache.log4j.Logger;

/**
 * The class <tt>Shoe</tt> represents a Shoe entity to be stored in the
 * database.
 */
@HibernatePersistent
public final class Shoe extends AbstractLockable {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(Shoe.class);

  /**
   * The primary key for this particular shoe.
   */
  @Key
  private long shoeid;

  /**
   * The make of the shoe.
   */
  private String make;

  /**
   * The model of the shoe.
   */
  private String model;

  /**
   * The size of the shoe.
   */
  private int size;

  /**
   * The colour of the shoe.
   */
  private String colour;

  /**
   * The material the shoe is made from.
   */
  private String material;
  
  /**
   * Creates an instance of the <code>Shoe</code> model.
   */
  public Shoe() {
    super();
  }

  /**
   * Get the identifying attribute of this shoe object.
   *
   * @return long Shoe ID.
   */
  public long getShoeid() {
    return shoeid;
  }

  /**
   * Set the identifying attribute of this object.
   *
   * @param newShoeid The identifying attribute for this object.
   */
  public void setShoeid(final long newShoeid) {
    this.shoeid = newShoeid;
  }

  /**
   * Get the make of the shoes.
   *
   * @return String Shoes make.
   */
  public String getMake() {
    return make;
  }

  /**
   * Set the make of the shoes.
   *
   * @param newMake The make of the shoes.
   */
  public void setMake(final String newMake) {
    this.make = newMake;
  }

  /**
   * Get the model of the shoes.
   *
   * @return String Shoes model.
   */
  public String getModel() {
    return model;
  }

  /**
   * Set the model of the shoes.
   *
   * @param newModel The model of the shoes.
   */
  public void setModel(final String newModel) {
    this.model = newModel;
  }

  /**
   * Get the size of the shoe.
   *
   * @return int Shoe size.
   */
  public int getSize() {
    return size;
  }

  /**
   * Set the size of this pair of shoes.
   *
   * @param newSize The size of this shoe.
   */
  public void setSize(final int newSize) {
    this.size = newSize;
  }

  /**
   * Get the colour of this pair of shoes.
   *
   * @return String Shoe colour.
   */
  public String getColour() {
    return colour;
  }

  /**
   * Set the colour of this pair of shoes.
   *
   * @param newColour Shoe colour.
   */
  public void setColour(final String newColour) {
    this.colour = newColour;
  }

  /**
   * Get the material that the shoes are made from.
   *
   * @return String Shoe material.
   */
  public String getMaterial() {
    return material;
  }

  /**
   * Set the material that the shoes are made from.
   *
   * @param newMaterial Shoe material.
   */
  public void setMaterial(final String newMaterial) {
    this.material = newMaterial;
  }
}
