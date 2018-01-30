/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.model.HibernateResource;
import com.logitopia.jmortar.core.persistence.dao.model.Resource;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteDuplicateEntryException;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteLockingException;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;

/**
 * The <tt>WritableHibernateDataAccessComponentImpl</tt> class is an implementation of
 * <tt>WritableDataAccessComponent</tt> that uses hibernate to store persistent data.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 * @param <T> The type of model that this component deals with.
 */
public final class WritableHibernateDataAccessComponentImpl<T>
        extends AbstractWritableDataAccessComponent<T, HibernateResource>
        implements WritableDataAccessComponent<T> {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(WritableHibernateDataAccessComponentImpl.class);

  /**
   * The hibernate session factory.
   */
  private SessionFactory sessionFactory;

  /**
   * Block Default Constructor.
   */
  private WritableHibernateDataAccessComponentImpl() {
    super(null);
  }

  /**
   * Create an instance of <tt>AbstractWritableHibernateDataAccessComponent</tt>
   * with a <tt>SessionFactory</tt>.
   *
   * @param newResource The persistent resource.
   */
  public WritableHibernateDataAccessComponentImpl(
          final HibernateResource newResource) {
    super(newResource);
    sessionFactory = newResource.getSessionFactory();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(final T newModel) throws DataAccessWriteLockingException,
          DataAccessWriteDuplicateEntryException {
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();

    try {
      session.save(newModel);
      tx.commit();
    } catch (HibernateOptimisticLockingFailureException lockingEx) {
      throw new DataAccessWriteLockingException(lockingEx.getMessage());
    } catch (ConstraintViolationException cvEx) {
      Throwable constraintViolation = cvEx.getCause();
      if (constraintViolation != null) {
        String message = constraintViolation.getMessage();
        if (message.startsWith("Duplicate entry")) {
          throw new DataAccessWriteDuplicateEntryException(message);
        }
      }
    } catch (StaleObjectStateException staleStateEx) {
      /* NOTE: As of hibernate 4, this exception gets thrown if we try to change an entity where the version number 
       * is out of step.
       */
      throw new DataAccessWriteLockingException(staleStateEx.getMessage());
    } finally {
      session.close();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(final T newModel) throws DataAccessWriteLockingException {
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();

    try {
      session.update(newModel);
      tx.commit();
    } catch (HibernateOptimisticLockingFailureException lockingEx) {
      throw new DataAccessWriteLockingException(lockingEx.getMessage());
    } catch (StaleObjectStateException staleStateEx) {
      /* NOTE: As of hibernate 4, this exception gets thrown if we try to change an entity where the version number 
       * is out of step.
       */
      throw new DataAccessWriteLockingException(staleStateEx.getMessage());
    } finally {
      session.close();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(final T newModel) throws DataAccessWriteLockingException {
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();

    try {
      session.delete(newModel);
      tx.commit();
    } catch (HibernateOptimisticLockingFailureException lockingEx) {
      throw new DataAccessWriteLockingException(lockingEx.getMessage());
    } catch (StaleObjectStateException staleStateEx) {
      /* NOTE: As of hibernate 4, this exception gets thrown if we try to change an entity where the version number 
       * is out of step.
       */
      throw new DataAccessWriteLockingException(staleStateEx.getMessage());
    } finally {
      session.close();
    }
  }
}
