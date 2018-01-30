/**
 * Title: ParentModel.java
 *
 * Author: Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt; Date Created: 29-May-2016
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.mock;

import org.apache.log4j.Logger;

/**
 * The <tt>ParentModel</tt> class is an implementation that defines a parent model (i.e. a model who has a child entity
 * as one of its members.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class ParentModel {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(ParentModel.class);
  
  private Shoe childOne;
  
  private Shoe childTwo;

  public ParentModel() {
  }

  public ParentModel(final Shoe one, final Shoe two) {
    childOne = one;
    childTwo = two;
  }

  public Shoe getChildOne() {
    return childOne;
  }

  public void setChildOne(Shoe childOne) {
    this.childOne = childOne;
  }

  public Shoe getChildTwo() {
    return childTwo;
  }

  public void setChildTwo(Shoe childTwo) {
    this.childTwo = childTwo;
  }
  
  
}
