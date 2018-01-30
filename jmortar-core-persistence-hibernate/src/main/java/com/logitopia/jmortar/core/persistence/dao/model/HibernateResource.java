package com.logitopia.jmortar.core.persistence.dao.model;

import org.hibernate.SessionFactory;

/**
 * A hibernate-specific <tt>Resource</tt>.
 */
public class HibernateResource implements Resource {

    /**
     * Hibernate Session Factory.
     */
    private SessionFactory sessionFactory;

    /**
     * Default Constructor. Create a hibernate-specific <tt>Resource</tt>.
     *
     * @param newSessionFactory A new session factory.
     */
    public HibernateResource(final SessionFactory newSessionFactory) {
        sessionFactory = newSessionFactory;
    }

    /**
     * Get the session factory.
     *
     * @return The <tt>SessionFactory</tt>.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
