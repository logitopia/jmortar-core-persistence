package com.logitopia.jmortar.core.persistence.fixtures.builder;

import com.logitopia.jmortar.core.persistence.builder.DACBuilder;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.fixtures.builder.dao.InventedResource;
import com.logitopia.jmortar.core.persistence.fixtures.builder.dao.ReadableInventedDataAccessComponentImpl;
import com.logitopia.jmortar.core.persistence.fixtures.builder.dao.WritableInventedDataAccessComponentImpl;

import java.util.List;
import java.util.Map;

public final class InventedDACBuilder implements DACBuilder<InventedResource> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadableDataAccessComponent buildReadableDAC(InventedResource resource,
            List<String> keyFields, QueryFactory queryFactory) {
        return new ReadableInventedDataAccessComponentImpl(queryFactory,
                resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadableDataAccessComponent buildReadableDAC(InventedResource resource,
            List<String> keyFields, QueryFactory queryFactory, Map queryItemBuilders) {
        return new ReadableInventedDataAccessComponentImpl(queryItemBuilders,
                queryFactory,
                resource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WritableDataAccessComponent buildWritableDAC(InventedResource resource) {
        return new WritableInventedDataAccessComponentImpl(resource);
    }
}
