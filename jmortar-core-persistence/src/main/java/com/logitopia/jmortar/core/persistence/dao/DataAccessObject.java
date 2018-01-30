/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao;

import com.logitopia.jmortar.core.persistence.response.DataResponse;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import java.util.List;
import java.util.Map;

/**
 * The <tt>DataAccessObject</tt> (DAO) interface defines the behaviour for all
 * DAO within the system. This will serve to provide an abstract interface to
 * the database. This will provide specific data operations without exposing
 * details of the database. This interface has been designed to allow JPA IoC
 * through the use of JPA-specific abstract implementations.
 *
 * @author s.cheesley
 *
 * @param <T> The type of model being stored.
 */
public interface DataAccessObject<T> {
  
  /**
   * Add an entity to the persistent storage. The values provided will be used to build the entity. The value structure
   * will consist of a map of all values to be stored. Any child entities will have their keys stored as a Map (this
   * will be passed on to the child entity persistence manager).
   *
   * @param values The values that make up the entities key.
   * @return The <tt>DataResponse</tt> that contains the response to the operation.
   */
  DataResponse addEntity(Map<String, Object> values);

  /**
   * Delete an entity to the persistent storage.
   *
   * @param keyValues The values that make up the entities unique key. That model will be deleted.
   * @return The <tt>DataResponse</tt> that contains the response to the operation.
   */
  DataResponse deleteEntity(Map<String, Object> keyValues);

  /**
   * Update the given entity in the persistent storage.
   *
   * @param updatedEntity The entity to update in persistent storage.
   * @return The <tt>DataResponse</tt> that contains the response to the operation.
   */
  DataResponse updateEntity(T updatedEntity);

  /**
   * Get a list of all entities that are stored within the persistent storage.
   *
   * @return A List of all users.
   */
  List<T> getAllEntities();
  
  /**
   * Get a list of all entities that are stored within the persistent storage.
   *
   * @return A List of all users.
   */
  List<T> getAllEntities(int pageNumber, int pageSize);

  /**
   * Get a specific entity <tt>T</tt> based on the key values that have been passed in.
   *
   * @param keyValues The key values to search on.
   * @return The entity <tt>T</tt> from persistent storage.
   */
  T getEntity(Map<String, Object> keyValues);

  /**
   * Find a list of entities based on a given query.
   *
   * @param query The query to find entities on.
   * @return A <tt>List</tt> of the entities that were found.
   */
  List<T> findEntities(Query query);
  
  /**
   * Find a list of entities based on a given query.
   *
   * @param query The query to find entities on.
   * @param pageNumber The number of the page that you wish to retrieve.
   * @param pageSize The size of the page that you wish to retrieve.
   * @return A <tt>List</tt> of the entities that were found.
   */
  List<T> findEntities(Query query, int pageNumber, int pageSize);
}
