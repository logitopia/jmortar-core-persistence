/* 
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.handler.DACQueryItemBuilder;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.model.HibernateResource;
import com.logitopia.jmortar.core.persistence.dao.model.Query;
import com.logitopia.jmortar.core.persistence.dao.model.impl.QueryFactoryRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * The <tt>ReadableHibernateDataAccessComponentImpl</tt> class is an implementation of
 * <tt>ReadableDataAccessComponent</tt> that uses hibernate to retrieve persistent data.
 *
 * @param <T> The type of model that this component deals with.
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public final class ReadableHibernateDataAccessComponentImpl<T>
        extends AbstractReadableDataAccessComponent<T, org.hibernate.Query, Class, Session, HibernateResource>
        implements ReadableDataAccessComponent<T> {

    /**
     * The logger for this class.
     */
    public static final Logger ABSTRACT_LOG
            = Logger.getLogger(ReadableHibernateDataAccessComponentImpl.class);

    /**
     * The hibernate session factory.
     */
    private final SessionFactory sessionFactory;

    /**
     * The fields in the model that comprise its unique key.
     */
    private List<String> keyFields;

    /**
     * Custom Query Item Builders. Create a Readable Hibernate Data Access Component that has custom
     * query item builders defined.
     *
     * @param newKeyFields         The fields that represent the entities unique key.
     * @param newQueryItemBuilders The custom query item builders.
     * @param newQueryFactory      The factory that builds the domain factory.
     * @param newResource          The payload that contains resources that the hibernate DAC needs to persist.
     */
    public ReadableHibernateDataAccessComponentImpl(final List<String> newKeyFields,
            final Map<String, DACQueryItemBuilder> newQueryItemBuilders,
            final QueryFactory<org.hibernate.Query, Class, Session> newQueryFactory,
            final HibernateResource newResource) {
        super(newQueryItemBuilders, newQueryFactory, newResource);
        sessionFactory = newResource.getSessionFactory();
        keyFields = newKeyFields;
    }

    /**
     * Default Constructor. Create a Readable Hibernate Data Access Component that uses default query
     * builders.
     *
     * @param newKeyFields      The fields that represent the entities unique key.
     * @param newQueryFactory   The factory that builds the domain query.
     * @param newResource       The payload that contains resources that the hibernate DAC needs to persist.
     */
    public ReadableHibernateDataAccessComponentImpl(final List<String> newKeyFields,
            final QueryFactory<org.hibernate.Query, Class, Session> newQueryFactory,
            final HibernateResource newResource) {
        super(newQueryFactory, newResource);
        sessionFactory = newResource.getSessionFactory();
        keyFields = newKeyFields;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getKeyFields() {
    /* Return a defensive copy (just in case) */
        return new ArrayList<>(keyFields);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll(Class newClazz) {
        Session session = sessionFactory.openSession();

        List<T> personList = getQueryFactory().createGetAllQuery(
                new QueryFactoryRequest<Class, Session>(newClazz, session)).list();
        session.close();
        return personList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> getAll(Class newClazz, int pageNumber, int pageSize) {
        Session session = sessionFactory.openSession();

        org.hibernate.Query query = getQueryFactory().createGetAllQuery(
                new QueryFactoryRequest<Class, Session>(newClazz, session));
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<T> personList = query.list();
        session.close();
        return personList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getEntityByID(Class newClazz, long id) {
        Session session = sessionFactory.openSession();
        T result = (T) session.get(newClazz, id);
        session.close();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> find(Class newClazz, Query findFilter) {
        Session session = sessionFactory.openSession();
        List<T> personList = getQueryFactory().createQuery(findFilter,
                new QueryFactoryRequest<Class, Session>(newClazz, session)).list();
        session.close();
        return personList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> find(Class newClazz, Query findFilter, int pageNumber, int pageSize) {
        Session session = sessionFactory.openSession();

        org.hibernate.Query query = getQueryFactory().createQuery(findFilter,
                new QueryFactoryRequest<Class, Session>(newClazz, session));
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<T> resultList = query.list();
        session.close();
        return resultList;
    }
}
