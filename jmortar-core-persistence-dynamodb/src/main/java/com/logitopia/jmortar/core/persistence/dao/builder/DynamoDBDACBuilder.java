package com.logitopia.jmortar.core.persistence.dao.builder;

import com.logitopia.jmortar.core.persistence.builder.DACBuilder;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.impl.ReadableDynamoDBDataAccessComponentImpl;
import com.logitopia.jmortar.core.persistence.dao.component.impl.WritableDynamoDBDataAccessComponentImpl;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.model.DynamoDBResource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Creating a DAC Builder that creates components for the DynamoDB implementation.
 */
@Component("dynamoDBDACBuilder")
public final class DynamoDBDACBuilder implements DACBuilder<DynamoDBResource> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadableDataAccessComponent buildReadableDAC(DynamoDBResource dynamoDBResource,
            List<String> keyFields,
            QueryFactory queryFactory) {
        return new ReadableDynamoDBDataAccessComponentImpl(keyFields,
                queryFactory, dynamoDBResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadableDataAccessComponent buildReadableDAC(DynamoDBResource dynamoDBResource,
            List<String> keyFields,
            QueryFactory queryFactory,
            Map queryItemBuilders) {
        return new ReadableDynamoDBDataAccessComponentImpl(keyFields,
                queryItemBuilders, queryFactory, dynamoDBResource);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WritableDataAccessComponent buildWritableDAC(DynamoDBResource resource) {
        return new WritableDynamoDBDataAccessComponentImpl(resource);
    }
}
