/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.logitopia.jmortar.core.persistence.dao.factory.attributevalue.DynamoDBAttributeValueHandler;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryFactoryRequest;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * A <tt>DynamoDBQueryFactory</tt> is a concrete implementation of <tt>QueryFactory</tt> that builds
 * Amazon DynamoDB specific queries.
 * 
 * Sorting is achieved via a query (i.e. date LESS THAN OR EQUAL TO last 15 days)
 *
 * @author Stephen Cheesley
 */
public final class DynamoDBQueryFactory<T>
        implements QueryFactory<DynamoDBQueryExpression<T>, Class, Object> {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(DynamoDBQueryFactory.class);

  /**
   * The handler for building attribute values for our order item attribute values.
   */
  private DynamoDBAttributeValueHandler attributeValueHandler;

  /**
   * Default Constructor. Create the Query Factory with the required <tt>AttributeValue</tt>
   * handler.
   *
   * @param newAttributeValueHandler The required <tt>AttributeValue</tt> handler.
   */
  public DynamoDBQueryFactory(final DynamoDBAttributeValueHandler newAttributeValueHandler) {
    attributeValueHandler = newAttributeValueHandler;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DynamoDBQueryExpression<T> createGetAllQuery(
          final QueryFactoryRequest<Class, Object> request) {
    if (request != null) {
      return new DynamoDBQueryExpression<T>();
    } else {
      throw new IllegalArgumentException("The request is null");
    }
  }

  /**
   * Load query item comparator. Load a hibernate-specific HQL comparator.
   *
   * @param inComparator The comparator to load a value for.
   * @return The HQL Comparator.
   */
  private String loadQueryItemComparator(QueryItemComparator inComparator) {
    String result = null;

    if (inComparator != null) {
      switch (inComparator) {
        case EQUALS:
          result = "=";
          break;
        case LESS:
          result = "<";
          break;
        case LESS_EQ:
          result = "<=";
          break;
        case MORE:
          result = ">";
          break;
        case MORE_EQ:
          result = ">=";
          break;
        case NEQUALS:
          result = "<>";
          break;
        case LIKE:
          result = "LIKE";
          break;
      }
    } else {
      LOG.error("Unable to resolve query item comparator - input comparator "
              + "is missing.");
    }

    return result;
  }

  /**
   * Transform a query item to an HSQL query.
   *
   * @param queryItem A query item to transform.
   * @return Map The HSQL query string built.
   */
  private Map<String, Object> transformQueryItemToString(
          final QueryItem queryItem) {
    Map<String, Object> result = new HashMap<>();
    StringBuilder queryString = new StringBuilder();

    queryString.append(queryItem.getField()).append(" ")
            .append(loadQueryItemComparator(queryItem.getComparator()))
            .append(" ").append(":").append(queryItem.getField());

    Object value = queryItem.getValue();
    result.put(queryItem.getField(), value);

    result.put("queryItemString", queryString.toString());
    return result;
  }

  /**
   * Transform a query to a DynamoDB query.
   *
   * @param query The query to transform.
   * @return Map The HSQL query string transformed.
   */
  private Map<String, Object> transformQueryToString(final Query query) {
    Map<String, Object> result = new HashMap<>();
    StringBuilder queryString = new StringBuilder();

    List<QueryItem> items = query.getQuery();
    int count = 0;

    for (QueryItem item : items) {
      if (items.size() > 1) {
        queryString.append("(");
      }
      /* Build the query item */
      Map<String, Object> queryItem = transformQueryItemToString(item);
      queryString.append(queryItem.get("queryItemString"));
      queryItem.remove("queryItemString");
      result.putAll(queryItem);
      
      if (items.size() > 1) {
        queryString.append(")");
      }
      if (count < items.size() - 1) {
        queryString.append(" ").append(query.getQueryType()).append(" ");
      }
      count++;
    }

    result.put("queryString", queryString.toString());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DynamoDBQueryExpression createQuery(final Query query,
          final QueryFactoryRequest<Class, Object> request) {
    Map<String, Object> queryItems = transformQueryToString(query);
    String queryString = queryItems.get("queryString").toString();

    queryItems.remove("queryString");

    Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();

    for (String key : queryItems.keySet()) {
      eav.put(":" + key, attributeValueHandler.buildValue(queryItems.get(key)));
    }

    DynamoDBQueryExpression<T> queryExpression = new DynamoDBQueryExpression<T>()
            .withKeyConditionExpression(queryString)
            .withExpressionAttributeValues(eav);

    return queryExpression;
  }

}
