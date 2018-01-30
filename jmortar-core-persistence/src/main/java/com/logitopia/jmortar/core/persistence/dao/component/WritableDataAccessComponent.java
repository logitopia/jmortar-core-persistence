/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component;

import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteDuplicateEntryException;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteLockingException;

/**
 * The <tt>WritableDataAccessComponent</tt> interface is a ...TODO
 *
 * @author &lt;Stephen Cheesley stephen.cheesley@gmail.com&gt;
 * @param <T> The business model element to be stored or loaded with data from
 * the database.
 */
public interface WritableDataAccessComponent<T> {

  /**
   * The <tt>save</tt> method will take a generic instance of a business model
   * and save a new instance of this object to the database. The save operation
   * is a hard-write meaning that it will override an existing entry.
   *
   * @param newModel The business model to be saved.
   * 
   * @throws DataAccessWriteLockingException When the write fails for optimistic locking.
   * @throws DataAccessWriteDuplicateEntryException When the write fails because the entry already exists.
   */
  void save(T newModel) throws DataAccessWriteLockingException,
          DataAccessWriteDuplicateEntryException;

  /**
   * The <tt>update</tt> method will take an instance of a business model and
   * update any differing records for an existing entry in the database. This
   * mirrors the UPDATE sql command.
   *
   * @param newModel The business model to be updated.
   * 
   * @throws DataAccessWriteLockingException When the write fails for optimistic locking.
   */
  void update(T newModel) throws DataAccessWriteLockingException;

  /**
   * The <tt>delete</tt> command will attempt to find the referenced business
   * model object and delete it from the database.
   *
   * @param newModel The business model to be deleted.
   * 
   * @throws DataAccessWriteLockingException When the write fails for optimistic locking.
   */
  void delete(T newModel) throws DataAccessWriteLockingException;
}
