/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory;

import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryFactoryRequest;

/**
 * A <tt>FactoryCallQueryFactory</tt> is a query factory that builds a domain query based upon
 * supplied criteria.
 *
 * @author Stephen Cheesley
 * 
 * @param <T> The domain query to return.
 * @param <U> The type that represents entity in the <tt>>QueryFactoryRequest</tt> model.
 * @param <V> The type of the factory that will be used to build the query.
 */
public interface QueryFactory<T, U, V> {

  /**
   * Create a query to get all records for a given entity.
   *
   * @param request The request containing the parameters of the new query.
   * @return The fully constructed domain query.
   */
  public T createGetAllQuery(QueryFactoryRequest<U, V> request);

  /**
   * Create the implementation query from the given jmortar query.
   *
   * @param query The query to build from.
   * @param request The request containing the parameters of the new query.
   * @return The fully constructed domain query.
   */
  public T createQuery(Query query, QueryFactoryRequest<U, V> request);
}
