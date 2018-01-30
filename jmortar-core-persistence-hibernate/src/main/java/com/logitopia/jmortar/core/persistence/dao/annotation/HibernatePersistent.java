package com.logitopia.jmortar.core.persistence.dao.annotation;

import com.logitopia.jmortar.core.persistence.annotation.Persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that signifies that the class is a model that is persisted using Hibernate.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Persistent
public @interface HibernatePersistent {

    /**
     * Set the name of the session factory bean.
     *
     * @return The session factory bean name.
     */
    String sessionFactory() default "sessionFactory";
}
