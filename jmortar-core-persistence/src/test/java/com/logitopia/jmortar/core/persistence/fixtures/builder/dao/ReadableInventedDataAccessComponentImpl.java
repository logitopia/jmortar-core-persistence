package com.logitopia.jmortar.core.persistence.fixtures.builder.dao;

import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.handler.DACQueryItemBuilder;
import com.logitopia.jmortar.core.persistence.dao.component.impl.AbstractReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.fixtures.builder.query.InventedQuery;

import java.util.List;
import java.util.Map;

/**
 * A test data access component for our builder to work with.
 */
public class ReadableInventedDataAccessComponentImpl<T>
        extends AbstractReadableDataAccessComponent<T, InventedQuery, Class, String, InventedResource>
        implements ReadableDataAccessComponent<T> {

    /**
     * Test Constructor.
     *
     * @param newQueryItemBuilders
     * @param newQueryFactory
     * @param newResource
     */
    public ReadableInventedDataAccessComponentImpl(Map<String, DACQueryItemBuilder> newQueryItemBuilders,
            QueryFactory<InventedQuery, Class, String> newQueryFactory,
            InventedResource newResource) {
        super(newQueryItemBuilders, newQueryFactory, newResource);
    }

    /**
     * Test Constructor.
     *
     * @param newQueryFactory
     * @param newResource
     */
    public ReadableInventedDataAccessComponentImpl(QueryFactory<InventedQuery, Class, String> newQueryFactory,
            InventedResource newResource) {
        super(newQueryFactory, newResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll(Class newClazz) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll(Class newClazz, int pageNumber, int pageSize) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getEntityByID(Class newClazz, long id) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> find(Class newClazz, Query findFilter) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> find(Class newClazz, Query findFilter, int pageNumber, int pageSize) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getKeyFields() {
        return null;
    }
}
