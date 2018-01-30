/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component;

import com.logitopia.jmortar.core.persistence.dao.exception.DataAccessComponentException;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import java.util.List;

/**
 * The <tt>ReadableDataAccessComponent</tt> interface is a data access
 * component. This is a strategy of a
 * <tt>com.logitopia.common.persistence.dao.DataAccessObject</tt> that is
 * responsible for providing components from reading data.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 * @param <T> The business model element to be stored or loaded with data from the
 * database.
 */
public interface ReadableDataAccessComponent<T> {

  /**
   * The <tt>getAll</tt> method will return a list of the business models,
   * having selected a full list of active objects from the database. Note, this
   * will only return active records (i.e. records that have not been deleted).
   *
   * @param newClazz The class of type T to be retrieved.
   * @return List - A list of the business model E stored in the database.
   */
  List<T> getAll(Class newClazz);
  
  /**
   * The <tt>getAll</tt> method will return a paginated list of the business models,
   * having selected a full list of active objects from the database. Note, this
   * will only return active records (i.e. records that have not been deleted).
   *
   * @param newClazz The class of type T to be retrieved.
   * @param pageNumber The number of the page that you wish to retrieve.
   * @param pageSize The size of the page that you wish to retrieve.
   * @return List - A list of the business model E stored in the database.
   */
  List<T> getAll(Class newClazz, int pageNumber, int pageSize);

  /**
   * The <tt>getEntityByID</tt> method will return an entity T that matches a
   * given ID. The ID and class are provided to tell Hibernate which table to
   * check and for what ID.
   *
   * @param newClazz The class of type T to be retrieved.
   * @param id The unique ID of the object to retrieve.
   * @return A business model / entity that matches the given id. A null object
   * will be returned if nothing matches that ID.
   */
  T getEntityByID(Class newClazz, long id);

  /**
   * The <tt>getEntityByID</tt> method will return an entity T that matches a
   * given ID. The ID and class are provided to tell Hibernate which table to
   * check and for what ID.
   *
   * @param newClazz The class of type T to be retrieved.
   * @param example An entity loaded with the key values of the object to be
   * retrieved.
   * @return A business model / entity that matches the given id. A null object
   * will be returned if nothing matches that ID.
   *
   * @throws DataAccessComponentException when data cannot be read or has
   * produced anomalous results.
   */
  T getEntityByID(Class newClazz, T example)
          throws DataAccessComponentException;

  /**
   * The <tt>find</tt> method will return a list of business models, having
   * selected a list of active objects from the database that match the given
   * filter. Note, this will only return active records (i.e. records that have
   * not been deleted).
   *
   * @param newClazz The class of type T to be retrieved.
   * @param findFilter The filter to apply to the find operation.
   * @return List - A list of the business model E stored in the database.
   */
  List<T> find(Class newClazz, Query findFilter);
  
  /**
   * The <tt>find</tt> method will return a list of business models, having
   * selected a list of active objects from the database that match the given
   * filter. Note, this will only return active records (i.e. records that have
   * not been deleted).
   *
   * @param newClazz The class of type T to be retrieved.
   * @param findFilter The filter to apply to the find operation.
   * @param pageNumber The number of the page that you wish to retrieve.
   * @param pageSize The size of the page that you wish to retrieve.
   * @return List - A list of the business model E stored in the database.
   */
  List<T> find(Class newClazz, Query findFilter, int pageNumber, int pageSize);
}
