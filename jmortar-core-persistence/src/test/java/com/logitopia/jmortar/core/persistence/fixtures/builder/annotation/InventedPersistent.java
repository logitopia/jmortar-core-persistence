package com.logitopia.jmortar.core.persistence.fixtures.builder.annotation;

import com.logitopia.jmortar.core.persistence.annotation.Persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A persistent implementation used for testing purposes.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Persistent
public @interface InventedPersistent {

    /**
     * Set the name of the session factory bean.
     *
     * @return The session factory bean name.
     */
    String credentialsProvider() default "none";
}
