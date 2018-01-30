package com.logitopia.jmortar.core.persistence.dao.builder;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.logitopia.jmortar.core.persistence.builder.ResourceFactory;
import com.logitopia.jmortar.core.persistence.builder.exception.MalformedPersistentModelException;
import com.logitopia.jmortar.core.persistence.dao.annotation.DynamoDBPersistent;
import com.logitopia.jmortar.core.persistence.dao.model.DynamoDBResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * A dynamoDB-specific resource factory.
 */
@Component("dynamoDBResourceFactory")
public class DynamoDBResourceFactory implements ResourceFactory<DynamoDBResource>, ApplicationContextAware {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(DynamoDBResourceFactory.class);

    /**
     * The Spring Application Context
     */
    private ApplicationContext applicationContext;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(final ApplicationContext newApplicationContext) throws BeansException {
        applicationContext = newApplicationContext;
    }

    /**
     * Get the credentials provider bean name.
     *
     * @param modelClass The class of the model we want to get the credentials provider for.
     * @return The bean name of the credentials provider.
     * @throws MalformedPersistentModelException
     */
    private String getCredentialsProviderBeanName(final Class modelClass) throws MalformedPersistentModelException {
        DynamoDBPersistent persistent = getDynamoDBPersistentAnnotation(modelClass);
        return persistent.credentialsProvider();
    }

    /**
     * Get the region container bean name.
     *
     * @param modelClass The class of the model we want to get the region container for.
     * @return The bean name of the region container.
     * @throws MalformedPersistentModelException
     */
    private String getRegionsBeanName(final Class modelClass) throws MalformedPersistentModelException {
        DynamoDBPersistent persistent = getDynamoDBPersistentAnnotation(modelClass);
        return persistent.regions();
    }

    /**
     * Get the no of threads to use in persistence.
     *
     * @param modelClass The class of the model we want to get the no of threads for.
     * @return The no of persistent threads.
     * @throws MalformedPersistentModelException
     */
    private int getNoOfThreads(final Class modelClass) throws MalformedPersistentModelException {
        DynamoDBPersistent persistent = getDynamoDBPersistentAnnotation(modelClass);

        return persistent.noOfThreads();
    }

    /**
     * Get the models persistent annotation
     *
     * @param modelClass The class of the model we want to get the persistent annotation for.
     * @return The persistent annotation.
     * @throws MalformedPersistentModelException
     */
    private DynamoDBPersistent getDynamoDBPersistentAnnotation(final Class modelClass) throws MalformedPersistentModelException {
        Annotation[] annotations = modelClass.getAnnotationsByType(DynamoDBPersistent.class);

        if (annotations.length == 1) {
            return (DynamoDBPersistent) annotations[0];
        } else if (annotations.length > 1) {
            throw new MalformedPersistentModelException(
                    "There are too many instances of the DynamoDBPersistent annotation on the model.");
        }

        throw new MalformedPersistentModelException(
                "There model is not annotated as persistent.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DynamoDBResource build(final Class modelClass) throws MalformedPersistentModelException {
        LOG.info("Building the DynamoDBResource for " + modelClass.getSimpleName());
        // 1. Get the resources bean name
        String credentialsProviderBeanName = getCredentialsProviderBeanName(modelClass);
        String regionsBeanName = getRegionsBeanName(modelClass);

        // 2. Retrieve the resources from the Spring Configuration.
        AWSCredentialsProvider awsCredentialsProvider = (AWSCredentialsProvider) applicationContext.getBean(credentialsProviderBeanName);
        Regions awsRegions = (Regions) applicationContext.getBean(regionsBeanName);
        int noOfThreads = getNoOfThreads(modelClass);
        LOG.info("Finished build the DynamoDBResource for " + modelClass.getSimpleName());

        return new DynamoDBResource(awsCredentialsProvider, awsRegions, noOfThreads);
    }
}
