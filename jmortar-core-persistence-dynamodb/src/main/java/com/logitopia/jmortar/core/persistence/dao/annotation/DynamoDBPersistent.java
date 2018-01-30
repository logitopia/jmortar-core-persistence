package com.logitopia.jmortar.core.persistence.dao.annotation;

import com.logitopia.jmortar.core.persistence.annotation.Persistent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that signifies that the class is a model that is persisted using DynamoDB.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Persistent
public @interface DynamoDBPersistent {

    /**
     * Set the name of the session factory bean.
     *
     * @return The session factory bean name.
     */
    String credentialsProvider() default "credProvider";

    /**
     * Set the name of the AWS Regions bean.
     *
     * @return The AWS regions bean name.
     */
    String regions() default "awsRegion";

    /**
     * Set the number of threads to use for persistent operations.
     *
     * @return The number of threads.
     */
    int noOfThreads() default 1;

    /**
     * Set the name of the Query Item builders Map bean.
     *
     * @return The Query Item builders Map bean.
     */
    String queryItemBuilders() default "none";
}
