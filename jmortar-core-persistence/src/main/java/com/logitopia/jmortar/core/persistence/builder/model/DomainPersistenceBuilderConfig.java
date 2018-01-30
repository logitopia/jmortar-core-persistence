package com.logitopia.jmortar.core.persistence.builder.model;

import com.logitopia.jmortar.core.persistence.builder.DACBuilder;
import com.logitopia.jmortar.core.persistence.builder.ResourceFactory;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;

import java.util.Map;

/**
 * A model that contains the domain configuration for the Persistence Configuration Builder. Saves on using a bunch of
 * maps in the configuration builder.
 */
public class DomainPersistenceBuilderConfig {

    /**
     * The resource factory that generates the domain resource.
     */
    private ResourceFactory resourceFactory;

    /**
     * The query factory to build into the persistent objects. This is specific to the domain ONLY, therefore it can be
     * injected into the configuration.
     */
    private QueryFactory queryFactory;

    /**
     * The data access component builder configured for the type of annotation we are building for.
     */
    private DACBuilder dacBuilder;

    /**
     * The query item builders.
     */
    private Map queryItemBuilders;

    /**
     * Default Constructor. Create a new instance of the DomainPersistenceBuilderConfig.
     *
     * @param newResourceFactory   The resource factory.
     * @param newQueryFactory      The query factory.
     * @param newDACBuilder        The data access component builder.
     * @param newQueryItemBuilders A map of Query Item Builders.
     */
    public DomainPersistenceBuilderConfig(final ResourceFactory newResourceFactory,
            final QueryFactory newQueryFactory,
            final DACBuilder newDACBuilder,
            final Map newQueryItemBuilders) {
        this.resourceFactory = newResourceFactory;
        this.queryFactory = newQueryFactory;
        this.dacBuilder = newDACBuilder;
        this.queryItemBuilders = newQueryItemBuilders;
    }

    /**
     * Default Constructor. Create a new instance of the DomainPersistenceBuilderConfig.
     *
     * @param newResourceFactory  The resource factory.
     * @param newQueryFactory     The query factory.
     * @param newDACBuilder       The data access component builder.
     */
    public DomainPersistenceBuilderConfig(final ResourceFactory newResourceFactory,
            final QueryFactory newQueryFactory,
            final DACBuilder newDACBuilder) {
        this.resourceFactory = newResourceFactory;
        this.queryFactory = newQueryFactory;
        this.dacBuilder = newDACBuilder;
    }

    /**
     * Get the resource factory for this domain.
     *
     * @return The resource factory.
     */
    public ResourceFactory getResourceFactory() {
        return resourceFactory;
    }

    /**
     * Get the query factory for this domain.
     *
     * @return The query factory
     */
    public QueryFactory getQueryFactory() {
        return queryFactory;
    }

    /**
     * Get the DAC builder strategy.
     *
     * @return The DAC Builder.
     */
    public DACBuilder getDacBuilder() {
        return dacBuilder;
    }

    /**
     * Get the query item builders.
     *
     * @return The query item builders.
     */
    public Map getQueryItemBuilders() {
        return queryItemBuilders;
    }
}
