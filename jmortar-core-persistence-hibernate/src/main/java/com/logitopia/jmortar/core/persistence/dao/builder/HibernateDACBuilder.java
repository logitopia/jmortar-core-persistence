package com.logitopia.jmortar.core.persistence.dao.builder;

import com.logitopia.jmortar.core.persistence.builder.DACBuilder;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.impl.ReadableHibernateDataAccessComponentImpl;
import com.logitopia.jmortar.core.persistence.dao.component.impl.WritableHibernateDataAccessComponentImpl;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.model.HibernateResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Creating a DAC Builder that creates components for the Hibernate implementation.
 */
@Component("hibernateDACBuilder")
public final class HibernateDACBuilder implements DACBuilder<HibernateResource> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadableDataAccessComponent buildReadableDAC(HibernateResource hibernateResource,
            List<String> keyFields, QueryFactory queryFactory) {
        return new ReadableHibernateDataAccessComponentImpl(keyFields, queryFactory, hibernateResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadableDataAccessComponent buildReadableDAC(HibernateResource hibernateResource,
            List<String> keyFields, QueryFactory queryFactory, Map queryItemBuilders) {
        return new ReadableHibernateDataAccessComponentImpl(keyFields,
                queryItemBuilders, queryFactory, hibernateResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WritableDataAccessComponent buildWritableDAC(HibernateResource hibernateResource) {
        return new WritableHibernateDataAccessComponentImpl(hibernateResource);
    }
}
