/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.builders;

import com.logitopia.jmortar.core.persistence.dao.model.Query;

/**
 * A Director from the GOF builder pattern that orchestrates the creation of a Query.
 *
 * @author Stephen Cheesley
 */
public class QueryDirector {

  /**
   * Orchestrate the building of a <tt>Query</tt>.
   *
   * @param builder The builder that controls how the object is built.
   * @return A <tt>Query</tt> in its final state.
   */
  public Query buildQuery(final QueryBuilder builder) {
    builder.buildQueryInstance();
    builder.buildMainQueryDetail();
    builder.buildQueryItems();

    return builder.getQuery();
  }
}
