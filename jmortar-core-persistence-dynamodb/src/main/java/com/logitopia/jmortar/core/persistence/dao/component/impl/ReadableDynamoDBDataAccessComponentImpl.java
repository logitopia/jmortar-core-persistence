/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedParallelScanList;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.handler.DACQueryItemBuilder;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.model.DynamoDBResource;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryFactoryRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <tt>ReadableDynamoDBDataAccessComponentImpl</tt> class is an implementation of
 * <tt>ReadableDataAccessComponent</tt> that uses Dynamo DB to retrieve persistent data.
 *
 * @param <T> The type of model that this component deals with.
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public class ReadableDynamoDBDataAccessComponentImpl<T>
        extends AbstractReadableDataAccessComponent<T, DynamoDBQueryExpression, Class, Object, DynamoDBResource>
        implements ReadableDataAccessComponent<T> {

    /**
     * The logger for this class.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(ReadableDynamoDBDataAccessComponentImpl.class);

    /**
     * The fields in the model that comprise its unique key.
     */
    private List<String> keyFields;

    /**
     * The credential provider that is used to create the dynamo DB client.
     */
    private AWSCredentialsProvider credentialProvider;

    /**
     * The region that the dynamo DB resides in.
     */
    private Regions awsRegion;

    /**
     * The number of threads to be taken up by parallel retrieval operations.
     */
    private int noOfThreads;

    /**
     * Default Constructor. Build the readable data access component with the required dynamo DB
     * components.
     *
     * @param newKeyFields    The key fields.
     * @param newQueryFactory The query factory.
     * @param newResource     The dynamoDB resource.
     */
    public ReadableDynamoDBDataAccessComponentImpl(
            final List<String> newKeyFields,
            final QueryFactory<DynamoDBQueryExpression, Class, Object> newQueryFactory,
            final DynamoDBResource newResource) {
        super(newQueryFactory, newResource);
        credentialProvider = newResource.getCredentialsProvider();
        keyFields = newKeyFields;
        awsRegion = newResource.getRegions();
        noOfThreads = newResource.getNoOfThreads();
    }

    /**
     * Default Constructor. Build the readable data access component with the required dynamo DB
     * components.
     *
     * @param newKeyFields The key fields of the model.
     * @param newQueryItemBuilders  The query item builders.
     * @param newQueryFactory       The query factory.
     */
    public ReadableDynamoDBDataAccessComponentImpl(
            final List<String> newKeyFields,
            final Map<String, DACQueryItemBuilder> newQueryItemBuilders,
            final QueryFactory<DynamoDBQueryExpression, Class, Object> newQueryFactory,
            final DynamoDBResource newResource) {
        super(newQueryItemBuilders, newQueryFactory, newResource);
        credentialProvider = newResource.getCredentialsProvider();
        keyFields = newKeyFields;
        awsRegion = newResource.getRegions();
        noOfThreads = newResource.getNoOfThreads();
    }

    /**
     * Create an AWS dynamo client to allow for connection to amazon.
     *
     * @return The configured client.
     */
    private AmazonDynamoDBClient createClient() {
        return new AmazonDynamoDBClient(credentialProvider).withRegion(awsRegion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getKeyFields() {
        return new ArrayList<>(keyFields);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll(final Class newClazz) {
        List<T> result = new ArrayList<>();
        AmazonDynamoDBClient client = createClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        PaginatedParallelScanList<T> scanResult
                = mapper.parallelScan(newClazz, new DynamoDBScanExpression(), noOfThreads);

    /* TODO - Update this by using a putAll on collection */
        for (T scan : scanResult) {
            result.add(scan);
        }

    /* Return a defensive copy */
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll(final Class newClazz, final int pageNumber, final int pageSize) {
        List<T> result = new ArrayList<>();

        AmazonDynamoDBClient client = createClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        DynamoDBScanExpression expression = new DynamoDBScanExpression();
        expression.withLimit(pageSize);

        PaginatedParallelScanList<T> scanResult
                = mapper.parallelScan(newClazz, expression, noOfThreads);

        int lastItem = pageNumber * pageSize;
        int prevPageLastItem = (pageNumber - 1) * pageSize;
        for (int i = prevPageLastItem; i < lastItem; i++) {
            result.add(scanResult.get(i));
        }

    /* Return a defensive copy */
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getEntityByID(final Class newClazz, final long id) {
        AmazonDynamoDBClient client = createClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        T result = (T) mapper.load(newClazz, id);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> find(final Class newClazz, final Query findFilter) {
        List<T> result;

        AmazonDynamoDBClient client = createClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        DynamoDBQueryExpression query = getQueryFactory().createQuery(findFilter,
                new QueryFactoryRequest<Class, Object>(newClazz, null));

        result = mapper.query(newClazz, query);

        return result;
    }

    @Override
    public List<T> find(final Class newClazz, final Query findFilter, int pageNumber, int pageSize) {
        List<T> result = new ArrayList<>();

        AmazonDynamoDBClient client = createClient();
        DynamoDBMapper mapper = new DynamoDBMapper(client);

        DynamoDBQueryExpression query = getQueryFactory().createQuery(findFilter,
                new QueryFactoryRequest<Class, Object>(newClazz, null));
        query.withLimit(pageSize);

        PaginatedQueryList<T> queryResult = mapper.query(newClazz, query);
        int lastItem = pageNumber * pageSize;
        int prevPageLastItem = (pageNumber - 1) * pageSize;
        for (int i = prevPageLastItem; i < lastItem; i++) {
            result.add(queryResult.get(i));
        }

        return result;
    }

}
