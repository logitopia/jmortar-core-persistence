package com.logitopia.jmortar.core.persistence.fixtures.builder.dao;

import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.impl.AbstractWritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteDuplicateEntryException;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteLockingException;

/**
 * Writable Data Access Component.
 */
public class WritableInventedDataAccessComponentImpl<T>
        extends AbstractWritableDataAccessComponent<T, InventedResource>
        implements WritableDataAccessComponent<T> {

    /**
     * Default Constructor. Create a writable data access component using the domain resource.
     *
     * @param newResource The domain <tt>Resource</tt>.
     */
    public WritableInventedDataAccessComponentImpl(InventedResource newResource) {
        super(newResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(T newModel) throws DataAccessWriteLockingException, DataAccessWriteDuplicateEntryException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(T newModel) throws DataAccessWriteLockingException {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(T newModel) throws DataAccessWriteLockingException {

    }
}
