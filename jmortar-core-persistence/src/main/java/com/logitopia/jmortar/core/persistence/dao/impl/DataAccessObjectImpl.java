/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.impl;

import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.exception.DataAccessComponentException;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteDuplicateEntryException;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteLockingException;
import com.logitopia.jmortar.core.persistence.response.DataResponse;
import com.logitopia.jmortar.core.persistence.response.impl.DataResponseImpl;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * The <tt>DataAccessObjectImpl</tt> class is an abstract implementation of <tt>DataAccessObject</tt> that manages
 * <tt>DataAccessComponent</tt> and persistent interaction.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class DataAccessObjectImpl<T> implements DataAccessObject<T> {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DataAccessObjectImpl.class);

  /**
   * Readable data access component.
   */
  private ReadableDataAccessComponent<T> readable;

  /**
   * Writable data access component.
   */
  private WritableDataAccessComponent<T> writable;

  /**
   * The class of entity that this manages.
   */
  private Class<T> type;

  /**
   * The child entity persistence managers.
   */
  private final Map<String, DataAccessObject> childDataAccessObjects;

  /**
   * Default constructor. This is blocked as the default construction is invalid.
   */
  private DataAccessObjectImpl() {
    childDataAccessObjects = new HashMap<>();
  }

  /**
   * Base Constructor. Create an empty DAO implementation. There is no read or write component to this DAO.
   *
   * @param newChildDataAccessObjects The subordinate data access objects that help to build this model.
   * @param newType Pass in the type (entity) that this DAO will be dealing with.
   */
  private DataAccessObjectImpl(final Map<String, DataAccessObject> newChildDataAccessObjects, final Class<T> newType) {
    if (newChildDataAccessObjects == null) {
      childDataAccessObjects = new HashMap<>();
    } else {
      childDataAccessObjects = newChildDataAccessObjects;
    }
    type = newType;
  }

  /**
   * Create a new read-only instance.
   *
   * @param newChildDataAccessObjects The subordinate data access objects that help to build this model.
   * @param newReadable The readable component to be used by this DAO.
   * @param newType Pass in the type (entity) that this DAO will be dealing with.
   */
  public DataAccessObjectImpl(final Map<String, DataAccessObject> newChildDataAccessObjects,
          final ReadableDataAccessComponent newReadable,
          final Class<T> newType) {
    this(newChildDataAccessObjects, newType);
    readable = newReadable;
  }

  /**
   * Create a write-only instance.
   *
   * @param newChildDataAccessObjects The subordinate data access objects that help to build this model.
   * @param newWritable The writable component to be used by this DAO.
   * @param newType Pass in the type (entity) that this DAO will be dealing with.
   */
  public DataAccessObjectImpl(final Map<String, DataAccessObject> newChildDataAccessObjects,
          final WritableDataAccessComponent newWritable,
          final Class<T> newType) {
    this(newChildDataAccessObjects, newType);
    writable = newWritable;
  }

  /**
   * Create a read/write instance
   *
   * @param newReadable The readable component to be used by this DAO.
   * @param newWritable The writable component to be used by this DAO.
   * @param newType Pass in the type (entity) that this DAO will be dealing with.
   */
  public DataAccessObjectImpl(final Map<String, DataAccessObject> newChildDataAccessObjects,
          final ReadableDataAccessComponent newReadable,
          final WritableDataAccessComponent newWritable,
          final Class<T> newType) {
    this(newChildDataAccessObjects, newType);
    readable = newReadable;
    writable = newWritable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataResponse addEntity(final Map<String, Object> values) {
    DataResponse result = new DataResponseImpl(DataResponse.FAIL_GENERAL);

    try {
      /* Build a new instance of the entity. */
      T entity = buildEntity(values);
      result = save(entity);
      if (result.getStatus() == DataResponse.SUCCESS) {
        LOG.info(String.format("Created entity %s", type.getSimpleName()));
        result.addAttribute(type.getSimpleName(), entity);
      }
    } catch (InstantiationException ex) {
      LOG.error("Unable to create a new instance of entity " + type.getSimpleName(), ex);
    } catch (IllegalAccessException ex) {
      LOG.error("Access issue on entity of type " + type.getSimpleName(), ex);
    } catch (NoSuchFieldException ex) {
      LOG.error("The field requested does not exist in entity " + type.getSimpleName(), ex);
    } catch (SecurityException ex) {
      LOG.error("Unable to write to field, a security issue has occured on entity " + type.getSimpleName(), ex);
    }

    return result;
  }

  /**
   * Build the entity based on the values provided. This method will generate the entity that will be stored in the
   * chosen persistent storage medium.
   *
   * @param values The values to load into the entity.
   * @return The built entity of type <tt>T</tt>.
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws NoSuchFieldException
   */
  private T buildEntity(final Map<String, Object> values)
          throws InstantiationException, IllegalAccessException, NoSuchFieldException {

    /* Create a new instance of the entity. */
    T result = type.newInstance();

    /* Set all of the passed values. */
    for (String fieldName : values.keySet()) {
      Field field = type.getDeclaredField(fieldName);
      field.setAccessible(true);

      Object value = values.get(fieldName);
      DataAccessObject dao = childDataAccessObjects.get(fieldName);
      if (dao != null) {
        /* The value is an identifier for the child entity. */
        Map<String, Object> key = (Map<String, Object>) value;
        value = dao.getEntity(key);
      }
      field.set(result, value);
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataResponse deleteEntity(final Map<String, Object> keyValues) {
    DataResponse result;
    if (writable != null) {
      T existing = getEntity(keyValues);
      if (existing != null) {

        try {
          writable.delete(existing);
          result = new DataResponseImpl(DataResponse.SUCCESS);
        } catch (DataAccessWriteLockingException ex) {
          /* Locking error has occurred! */
          T updatedModel = getUpdatedModel(existing);
          result = new DataResponseImpl(DataResponse.FAIL_LOCKED);
          result.addAttribute("updatedModel", updatedModel);
        }

      } else {
        result = new DataResponseImpl(DataResponse.FAIL_GENERAL);
        result.addAttribute("message", "Unable to find record to be deleted");
      }
    } else {
      result = new DataResponseImpl(DataResponse.FAIL_GENERAL);
      result.addAttribute("message", String.format("This type is not writable", type.getSimpleName()));
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DataResponse updateEntity(final T updatedEntity) {
    DataResponse result;
    T response = updatedEntity;

    if (writable != null) {
      try {
        writable.update(response);
        result = new DataResponseImpl(DataResponse.SUCCESS);
      } catch (DataAccessWriteLockingException ex) {
        /* Locking error has occurred! */
        T updatedModel = getUpdatedModel(response);
        result = new DataResponseImpl(DataResponse.FAIL_LOCKED);
        result.addAttribute("updatedModel", updatedModel);
      }
    } else {
      result = new DataResponseImpl(DataResponse.FAIL_GENERAL);
      result.addAttribute("message", "This type is not writable");
    }

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> getAllEntities() {
    return getAllEntities(-1, -1);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> getAllEntities(final int pageNumber, final int pageSize) {
    List<T> result = new ArrayList<>();
    if (readable != null) {
      if (pageNumber > -1 && pageSize > -1) {
        result = readable.getAll(type, pageNumber, pageSize);
      } else {
        result = readable.getAll(type);
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T getEntity(final Map<String, Object> keyValues) {
    T result = null;
    try {
      T template = buildEntity(keyValues);

      if (readable != null) {
        result = readable.getEntityByID(type, template);
      }
    } catch (DataAccessComponentException ex) {
      LOG.error("Unable to get entity by ID", ex);
    } catch (InstantiationException ex) {
      LOG.error("Unable to create a new instance of entity " + type.getSimpleName(), ex);
    } catch (IllegalAccessException ex) {
      LOG.error("Access issue on entity of type " + type.getSimpleName(), ex);
    } catch (NoSuchFieldException ex) {
      LOG.error("The field requested does not exist in entity " + type.getSimpleName(), ex);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> findEntities(final Query query) {
    return findEntities(query, -1, -1);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<T> findEntities(final Query query, final int pageNumber, final int pageSize) {
    List<T> result = new ArrayList<>();
    if (readable != null) {
      if (pageNumber > -1 && pageSize > -1) {
        result = readable.find(type, query, pageNumber, pageSize);
      } else {
        result = readable.find(type, query);
      }
    }
    return result;
  }

  /**
   * The <tt>save</tt> method will take a generic instance of a business model and save a new instance of this object to
   * the database. The save operation is a hard-write meaning that it will override an existing entry.
   *
   * @param newModel The business model to be saved.
   *
   * @return The response from the save operation.
   */
  private DataResponse save(T newModel) {
    DataResponse result = null;

    if (writable != null) {
      try {
        writable.save(newModel);
        result = new DataResponseImpl(DataResponse.SUCCESS);
      } catch (DataAccessWriteLockingException ex) {
        /* Locking error has occurred! */
        T updatedModel = getUpdatedModel(newModel);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("updatedModel", updatedModel);
        result = new DataResponseImpl(DataResponse.FAIL_LOCKED, attributes);
      } catch (DataAccessWriteDuplicateEntryException deEx) {
        result = new DataResponseImpl(DataResponse.FAIL_DUPLICATE);
      }
    }

    return result;
  }

  /**
   * Get the updated model from the database.
   *
   * @param originalModel The out-of-date model.
   * @return The updated model.
   */
  private T getUpdatedModel(final T originalModel) {
    T result = null;

    if (readable != null) {
      try {
        result = readable.getEntityByID(type, originalModel);
      } catch (DataAccessComponentException ex) {
        LOG.error("Unable to retrieve updated model", ex);
      }
    }

    return result;
  }
}
