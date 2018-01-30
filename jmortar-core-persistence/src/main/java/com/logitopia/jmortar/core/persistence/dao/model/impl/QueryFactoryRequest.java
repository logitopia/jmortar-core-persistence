/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.model.impl;

/**
 * A <tt>QueryFactoryRequest</tt> class describes a request to build a query.
 *
 * @author Stephen Cheesley
 *
 * @param <T> The type of the entity being used to build the query.
 * @param <U> The type of the factory that will be used to build the query.
 */
public class QueryFactoryRequest<T, U> {

  /**
   * The entity being used to build the query.
   */
  private T entity;

  /**
   * A factory to be used to create the query object.
   */
  private U factory;

  /**
   * Entity Request Constructor. Create a request to build a query where just an entity is required.
   *
   * @param newEntity The main entity that the query is for.
   */
  public QueryFactoryRequest(final T newEntity) {
    entity = newEntity;
  }

  /**
   * Entity and Factory Request Constructor. Create a request to build a query where an entity and a
   * factory are required.
   *
   * @param newEntity The main entity that the query is for.
   * @param newFactory The factory required to build a query.
   */
  public QueryFactoryRequest(final T newEntity, final U newFactory) {
    this(newEntity);
    factory = newFactory;
  }

  /**
   * Get the entity that the query is for.
   *
   * @return The entity.
   */
  public T getEntity() {
    return entity;
  }

  /**
   * Get the factory that will build the domain query.
   *
   * @return The factory.
   */
  public U getFactory() {
    return factory;
  }
}
