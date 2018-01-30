/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.handler.DACQueryItemBuilder;
import com.logitopia.jmortar.core.persistence.dao.component.handler.impl.DefaultDACQueryItemBuilder;
import com.logitopia.jmortar.core.persistence.dao.exception.DataAccessComponentException;
import com.logitopia.jmortar.core.persistence.dao.exception.DataAccessComponentExceptionSource;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.Resource;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryImpl;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryLogicalConjunction;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * The <tt>AbstractReadableDataAccessComponent</tt> class is an abstract implementation of a
 * <tt>ReadableDataAccessComponent</tt>.
 *
 * @param <T>  The model type that the readable data access component deals with.
 * @param <U>  The type of domain query that this DAC works with.
 * @param <V>
 * @param <W>
 * @param <T1> The domain type of the required <tt>Resource</tt>.
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public abstract class AbstractReadableDataAccessComponent<T, U, V, W, T1 extends Resource>
        extends AbstractDataAccessComponent<T1>
        implements ReadableDataAccessComponent<T> {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = Logger.getLogger(AbstractReadableDataAccessComponent.class);

    /**
     * A <tt>Map</tt> of query item builders to their model classes.
     */
    private Map<String, DACQueryItemBuilder> queryItemBuilders;

    /**
     * The query factory that will be responsible for building the domain query.
     */
    private QueryFactory<U, V, W> queryFactory;

    /**
     * Default Constructor. Create a readable DAC with query item builders and a query factory.
     *
     * @param newQueryItemBuilders The query item builders.
     * @param newQueryFactory      The query factory.
     */
    public AbstractReadableDataAccessComponent(
            final Map<String, DACQueryItemBuilder> newQueryItemBuilders,
            final QueryFactory<U, V, W> newQueryFactory,
            final T1 newResource) {
        this(newQueryFactory, newResource);
        if (newQueryItemBuilders != null) {
            queryItemBuilders = newQueryItemBuilders;
        }
    }

    /**
     * Default QueryItem builders. Create a readable DAC with query item builders and a query factory.
     *
     * @param newQueryFactory The query factory.
     */
    public AbstractReadableDataAccessComponent(final QueryFactory<U, V, W> newQueryFactory,
            final T1 newResource) {
        super(newResource);
        queryItemBuilders = new HashMap<>();
        if (newQueryFactory == null) {
            throw new IllegalArgumentException("Unable to create a Readable DAC without a QueryFactory");
        }
        queryFactory = newQueryFactory;
    }

    /**
     * Get the query item builders for this DAC.
     *
     * @return The query item builders.
     */
    public Map<String, DACQueryItemBuilder> getQueryItemBuilders() {
        return queryItemBuilders;
    }

    /**
     * Get the query factory for this DAC.
     *
     * @return The query factory.
     */
    protected QueryFactory<U, V, W> getQueryFactory() {
        return queryFactory;
    }

    /**
     * Produce a list of query items from the given model loaded with the key values.
     *
     * @param exampleModel The model with the key.
     * @return A list of query items.
     */
    private List<QueryItem> getQueryItems(final T exampleModel) {
        List<QueryItem> result = new ArrayList<>();
        Map<String, Object> keyItems = new LinkedHashMap<>();

        for (String fieldName : getKeyFields()) {
            try {
                Field field = exampleModel.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object value = field.get(exampleModel);
                keyItems.put(fieldName, value);
            } catch (IllegalArgumentException ex) {
                LOG.error("Argument unrecognized.", ex);
            } catch (IllegalAccessException ex) {
                LOG.error("Unable to access.", ex);
            } catch (NoSuchFieldException ex) {
                LOG.error("Field not recognised.", ex);
            } catch (SecurityException ex) {
                LOG.error("Security issue.", ex);
            }
        }

    /* Execute the QIB. */
        DACQueryItemBuilder builder = queryItemBuilders.get(exampleModel.getClass().getSimpleName());
        if (builder == null) {
            builder = new DefaultDACQueryItemBuilder();
        }
        result = builder.buildItems(keyItems);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getEntityByID(final Class newClazz, final T example)
            throws DataAccessComponentException {
        T result = null;

        /**
         * 1. Build the query.
         */
        List<QueryItem> queryItems = getQueryItems(example);
        Query query = new QueryImpl();
        query.setQueryType(QueryLogicalConjunction.AND);
        query.setQuery(queryItems);

        /**
         * 2. Find the entity.
         */
        List<T> results = find(example.getClass(), query);
        if (results.size() == 1) {
            result = results.get(0);
        } else {
            if (LOG.isTraceEnabled()) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String queryString = mapper.writeValueAsString(query);
                    LOG.trace(new StringBuilder("Query String:").append(queryString));
                } catch (JsonProcessingException ex) {
                    LOG.trace("Unable to process query - object cannot be processed to JSON.", ex);
                } finally {
                    mapper = null;
                }
            }
            if (results.size() < 1) {
                throw new DataAccessComponentException(
                        DataAccessComponentExceptionSource.READABLE,
                        "No results have been found.");
            } else {
                throw new DataAccessComponentException(
                        DataAccessComponentExceptionSource.READABLE,
                        "Too many results have been found.");
            }
        }

        return result;
    }

    /**
     * Get the fields from the model that comprise the natural key.
     *
     * @return A list of the fields that make up the key.
     */
    public abstract List<String> getKeyFields();
}
