/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.builders;

import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.QueryModel;

/**
 * A <tt>QueryBuilder</tt> is a test fixture that follows the GOF Builder pattern. This will create
 * complex test fixtures for testing.
 *
 * @author Stephen Cheesley
 */
public abstract class QueryBuilder<T extends QueryModel> {

  /**
   * The query that is being built.
   */
  protected Query<T> query;
  
  /**
   * Build the query implementation.
   */
  public abstract void buildQueryInstance();

  /**
   * Compile the main query items and assign them to the query.
   */
  public abstract void buildQueryItems();
  
  /**
   * Populate the query detail.
   */
  public abstract void buildMainQueryDetail();
  
  /**
   * Get the built query.
   *
   * @return The query that has been built.
   */
  public final Query getQuery() {
    return query;
  }
}
