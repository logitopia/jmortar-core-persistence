package com.logitopia.jmortar.core.persistence.dao.builder;

import com.logitopia.jmortar.core.persistence.builder.ResourceFactory;
import com.logitopia.jmortar.core.persistence.builder.exception.MalformedPersistentModelException;
import com.logitopia.jmortar.core.persistence.dao.annotation.HibernatePersistent;
import com.logitopia.jmortar.core.persistence.dao.model.HibernateResource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * A hibernate-specific resource factory.
 */
@Component("hibernateResourceFactory")
public class HibernateResourceFactory
        implements ResourceFactory<HibernateResource>, ApplicationContextAware {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(HibernateResourceFactory.class);

    private ApplicationContext applicationContext;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Get the models persistent annotation.
     *
     * @param modelClass The class of the model we want to get the persistent annotation for.
     * @return The persistent annotation.
     * @throws MalformedPersistentModelException
     */
    private HibernatePersistent getHibernatePersistentAnnoation(final Class modelClass)  throws MalformedPersistentModelException {
        Annotation[] annotations = modelClass.getAnnotationsByType(HibernatePersistent.class);

        if (annotations.length == 1) {
            return (HibernatePersistent) annotations[0];
        } else if (annotations.length > 1) {
            throw new MalformedPersistentModelException(
                    "There are too many instances of the HibernatePersistent annotation on the model.");
        }

        throw new MalformedPersistentModelException(
                "There model is not annotated as persistent.");
    }

    /**
     * Get the session factory bean name.
     *
     * @param modelClass The class of the model we want to get the session factory for.
     * @return The bean name of the session factory
     * @throws MalformedPersistentModelException
     */
    private String getSessionFactoryBeanName(final Class modelClass) throws MalformedPersistentModelException {
        HibernatePersistent persistent = getHibernatePersistentAnnoation(modelClass);
        return persistent.sessionFactory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HibernateResource build(final Class modelClass) throws MalformedPersistentModelException {
        // 1. Get the Session Factory bean name (either from the model or the default if the model doesn't have it).
        LOG.info("Building the HibernateResource for " + modelClass.getSimpleName());
        String sessionFactoryBeanName = getSessionFactoryBeanName(modelClass);

        // 2. Retrieve the Session Factory from the Spring Configuration.
        SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean(sessionFactoryBeanName);
        LOG.info("Finished build the HibernateResource for " + modelClass.getSimpleName());
        return new HibernateResource(sessionFactory);
    }
}
