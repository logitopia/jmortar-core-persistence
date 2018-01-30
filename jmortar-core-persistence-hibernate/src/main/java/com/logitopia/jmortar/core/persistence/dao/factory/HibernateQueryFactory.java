/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.factory;

import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.QueryItem;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryFactoryRequest;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemComparator;
import com.logitopia.jmortar.core.persistence.dao.model.type.QueryItemSortType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * A <tt>HibernateQueryFactory</tt> is a concrete implementation of <tt>QueryFactory</tt> that
 * builds hibernate-specific queries.
 *
 * @author Stephen Cheesley
 */
public final class HibernateQueryFactory
        implements QueryFactory<org.hibernate.Query, Class, Session> {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(HibernateQueryFactory.class);

  /**
   * Get the syntax for the getAll HQL query.
   *
   * @param clazz The implementation class we are searching for.
   *
   * @return String The HQL query to get all entries for a given entity.
   */
  private String getAllHQL(final Class entity) {
    StringBuilder result = new StringBuilder();

    result.append("from ").append(entity.getName());

    return result.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.hibernate.Query createGetAllQuery(final QueryFactoryRequest<Class, Session> request) {
    if (request != null) {
      Class entity = request.getEntity();
      Session factory = request.getFactory();

      /* TODO - We could move these checks into a private method (to be used by both parts). */
      if (entity != null && factory != null) {
        return factory.createQuery(getAllHQL(entity));
      } else {
        if (entity == null && factory == null) {
          throw new IllegalArgumentException("The request is missing entity and factory.");
        } else if (entity == null) {
          throw new IllegalArgumentException("The request is missing entity.");
        } else {
          throw new IllegalArgumentException("The request is missing factory.");
        }
      }
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
  private Map<String, Object> transformQueryItemToHQL(
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
   * Build the sort string for this particular query item.
   *
   * @param queryItem The query item that is being sorted.
   * @return The HQL sort <tt>String</tt>. This method will return null if SORT type is null.
   */
  private String buildQueryItemSort(final QueryItem queryItem) {    
    if (queryItem.getSortType() != null && queryItem.getSortType() != QueryItemSortType.NONE) {
      StringBuilder sort = new StringBuilder(queryItem.getField());
      if (queryItem.getSortType() != QueryItemSortType.UNSPECIFIED) {
        sort.append(" ").append(queryItem.getSortType().name().toLowerCase());
      }
      return sort.toString();
    }
    return null;
  }

  /**
   * Transform a query to an HSQL query.
   *
   * @param query The query to transform.
   * @return Map The HSQL query string transformed.
   */
  private Map<String, Object> transformQueryToHQL(final Query query) {
    Map<String, Object> result = new HashMap<>();
    StringBuilder queryString = new StringBuilder();

    List<QueryItem> items = query.getQuery();
    int count = 0;
    
    List<String> sortCond = new ArrayList<>();
    for (QueryItem item : items) {
      if (items.size() > 1) {
        queryString.append("(");
      }
      /* Build the query item */
      Map<String, Object> queryItem = transformQueryItemToHQL(item);
      queryString.append(queryItem.get("queryItemString"));
      queryItem.remove("queryItemString");
      result.putAll(queryItem);
      
      /* Build the sort */
      String sort = buildQueryItemSort(item);
      if (sort != null) {
        sortCond.add(sort);
      }
      
      if (items.size() > 1) {
        queryString.append(")");
      }
      if (count < items.size() - 1) {
        queryString.append(" ").append(query.getQueryType()).append(" ");
      }
      count++;
    }
    
    if (sortCond.size() > 0) {
      queryString.append(" ORDER BY ");
      for (int i=0; i < sortCond.size(); i++) {
        queryString.append(sortCond.get(i));
        if (i < sortCond.size() - 1) {
          queryString.append(", ");
        }
      }
    }

    result.put("queryString", queryString.toString());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public org.hibernate.Query createQuery(final Query query,
          final QueryFactoryRequest<Class, Session> request) {
    org.hibernate.Query result;

    Class entity = request.getEntity();
    Session factory = request.getFactory();

    if (entity != null && factory != null) {
      StringBuilder queryResult = new StringBuilder(getAllHQL(entity));

      Map<String, Object> queryItems = transformQueryToHQL(query);
      String queryString = queryItems.get("queryString").toString();

      /* Build the initial query */
      queryResult.append(" ").append("WHERE ").append(queryString);
      result = factory.createQuery(queryResult.toString());

      /* Use named parameters to prevent SQL Injection attack. */
      for (String key : queryItems.keySet()) {
        if (!key.equals("queryString")) {
          result.setParameter(key, queryItems.get(key));
        }
      }

      return result;
    } else {
      if (entity == null && factory == null) {
        throw new IllegalArgumentException("The request is missing entity and factory.");
      } else if (entity == null) {
        throw new IllegalArgumentException("The request is missing entity.");
      } else {
        throw new IllegalArgumentException("The request is missing factory.");
      }
    }
  }
}
