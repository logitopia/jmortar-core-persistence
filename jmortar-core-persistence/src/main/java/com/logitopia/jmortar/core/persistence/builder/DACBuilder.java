package com.logitopia.jmortar.core.persistence.builder;

import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.model.Resource;

import java.util.List;
import java.util.Map;

/**
 * A builder that creates a Data Access Component.
 *
 * @param <T> Resource type
 */
public interface DACBuilder<T extends Resource> {

    /**
     * Build the readable data access component.
     *
     * @param resource The resource to use to build the readable DAC.
     * @param keyFields The key fields on the model.
     * @param queryFactory The factory that will be used to build queries for the persistence method.
     *
     * @return The complete readable DAC.
     */
    ReadableDataAccessComponent buildReadableDAC(T resource, List<String> keyFields, QueryFactory queryFactory);

    /**
     * Build the readable data access component.
     *
     * @param resource The resource to use to build the readable DAC.
     * @param keyFields The key fields on the model.
     * @param queryFactory The factory that will be used to build queries for the persistence method.
     * @param queryItemBuilders The query item builders that must be used to construct the DAC.
     *
     * @return The complete readable DAC.
     */
    ReadableDataAccessComponent buildReadableDAC(T resource, List<String> keyFields, QueryFactory queryFactory, Map queryItemBuilders);

    /**
     * Build the writable data access component.
     *
     * @param resource The resource to use to build the writable DAC.
     * @return The complete writable DAC.
     */
    WritableDataAccessComponent buildWritableDAC(T resource);
}
