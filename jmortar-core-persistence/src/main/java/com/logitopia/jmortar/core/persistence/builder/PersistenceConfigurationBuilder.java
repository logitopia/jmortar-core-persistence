package com.logitopia.jmortar.core.persistence.builder;

import com.logitopia.jmortar.core.object.annotation.AnnotationSearcher;
import com.logitopia.jmortar.core.object.exception.InvalidAnnotationException;
import com.logitopia.jmortar.core.persistence.annotation.Ignore;
import com.logitopia.jmortar.core.persistence.annotation.Key;
import com.logitopia.jmortar.core.persistence.annotation.Persistent;
import com.logitopia.jmortar.core.persistence.annotation.PersistentChild;
import com.logitopia.jmortar.core.persistence.builder.exception.MalformedPersistentModelException;
import com.logitopia.jmortar.core.persistence.builder.model.DomainPersistenceBuilderConfig;
import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.factory.QueryFactory;
import com.logitopia.jmortar.core.persistence.dao.impl.DataAccessObjectImpl;
import com.logitopia.jmortar.core.persistence.dao.model.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A configuration builder that can setup persistence requirements.
 */
public class PersistenceConfigurationBuilder implements BeanFactoryPostProcessor {

    /**
     * The logger for this class.
     */
    public static final Logger LOG
            = LoggerFactory.getLogger(PersistenceConfigurationBuilder.class);

    /**
     * The executor service that this builder will use to source threads.
     */
    private ExecutorService primaryExecutor;

    /**
     * The annotation searcher that the builder will use to find annotated models.
     */
    private AnnotationSearcher annotationSearcher;

    /**
     * Maps the persistence configuration to the domain-specific configuration.
     */
    private Map<Class, DomainPersistenceBuilderConfig> domainConfig;

    /**
     * Default Constructor. Create a configuration builder with a setup annotation searcher.
     *
     * @param newAnnotationSearcher The annotation searcher to use.
     * @param newDomainConfig       Maps the domain persistent annotation to the domain configuration.
     */
    public PersistenceConfigurationBuilder(final AnnotationSearcher newAnnotationSearcher,
            final Map<Class, DomainPersistenceBuilderConfig> newDomainConfig,
            final int newThreadPoolSize) {
        annotationSearcher = newAnnotationSearcher;

        // Create a thread pool
        if (newThreadPoolSize == -1) {
            primaryExecutor = Executors.newFixedThreadPool(newThreadPoolSize);
        } else {
            primaryExecutor = Executors.newCachedThreadPool();
        }

        // Set domain-specific resources
        domainConfig = newDomainConfig;
    }

    /**
     * Get the domain configuration for the given model class.
     *
     * @param modelClass The model to get the domain configuration for.
     * @return The domain persistence builder configuration.
     */
    private DomainPersistenceBuilderConfig getDomainConfig(Class modelClass) throws MalformedPersistentModelException {
        for (Annotation annotation : modelClass.getAnnotations()) {
            for (Annotation type : annotation.annotationType().getAnnotations()) {
                if (type.annotationType() == Persistent.class) {
                    return domainConfig.get(annotation.annotationType());
                }
            }
        }

        throw new MalformedPersistentModelException("The model [" + modelClass.getSimpleName() + "] did not have a persistent annotation.");
    }

    /**
     * Find annotated models based on the persistent annotations that we have calibrated with.
     *
     * @return A list of annotated models.
     */
    private List<Class> findAnnotatedModels() {
        List<Class> models = new ArrayList<>();

        try {
            List<Class> filters = new ArrayList<>();
            filters.add(Ignore.class);
            models.addAll(annotationSearcher.search(new ArrayList<>(domainConfig.keySet()), filters));
        } catch (InvalidAnnotationException e) {
            LOG.error("Issue searching for one of the annotations", e);
        } catch (ClassNotFoundException e) {
            LOG.error("Unable to search completely for the annotation", e);
        }

        return models;
    }

    /**
     * Find a list of the key fields on an annotated model.
     *
     * @param modelClass The model to search for annotations.
     * @return A list of field names annotated with <tt>Key</tt>.
     */
    private List<String> findKeyFields(Class modelClass) {
        List<String> keyFields = new ArrayList<>();

        try {
            keyFields.addAll(annotationSearcher.searchClassForAnnotatedFieldNames(Key.class, modelClass));
        } catch (InvalidAnnotationException e) {
            LOG.error("Issue searching for one of the annotations", e);
        }
        return keyFields;
    }

    /**
     * Sort the annotated models so that the parent-level models are first and the persistent models with children are
     * second. This wouldn't normally be a private method but this allows us to test the sorting with the persistent
     * comparator attached.
     *
     * @param models The list of models to be sorted.
     */
    private void sortAnnotatedModels(List<Class> models) {
        Collections.sort(models, new PersistentComparator());
    }

    /**
     * Generate a domain-specific readable data access component.
     *
     * @param config     The configuration that contains building resources.
     * @param modelClass The class of the model we are building resources for.
     * @return The readable data access components.
     * @throws MalformedPersistentModelException
     */
    private ReadableDataAccessComponent generateReadableDAC(DomainPersistenceBuilderConfig config, Class modelClass)
            throws MalformedPersistentModelException {
        ReadableDataAccessComponent result;

        LOG.info("Building readable DAC for [" + modelClass.getName() + "]");

        // Get the builder that will construct the DAC.
        DACBuilder builder = config.getDacBuilder();

        // Get the components we need to build the readable DAC.
        QueryFactory queryFactory = config.getQueryFactory();
        Resource resource = config.getResourceFactory().build(modelClass);
        List<String> keyFields = findKeyFields(modelClass);

        if (config.getQueryItemBuilders() != null) {
            Map queryItemBuilders = config.getQueryItemBuilders();
            result = builder.buildReadableDAC(resource, keyFields, queryFactory, queryItemBuilders);
        } else {
            result = builder.buildReadableDAC(resource, keyFields, queryFactory);
        }

        return result;
    }

    /**
     * Generate a domain-specific writable data access component.
     *
     * @param config     The configuration that contains building resources.
     * @param modelClass The class of the model we are building resources for.
     * @return The generated domain-specific writable data access component.
     * @throws MalformedPersistentModelException
     */
    private WritableDataAccessComponent generateWritableDAC(DomainPersistenceBuilderConfig config, Class modelClass)
            throws MalformedPersistentModelException {
        WritableDataAccessComponent result;

        LOG.info("Building writable DAC for [" + modelClass.getName() + "]");

        // Get the builder that will construct the DAC.
        DACBuilder builder = config.getDacBuilder();

        // Get the components we need to build the readable DAC.
        Resource resource = config.getResourceFactory().build(modelClass);

        // Build the DAC
        result = builder.buildWritableDAC(resource);

        return result;
    }

    /**
     * Generate child DAO mapping.
     *
     * @param modelClass The class of the model were building.
     * @return The built map.
     * @throws InvalidAnnotationException
     */
    private Map<String, DataAccessObject> generateChildDAOMapping(final Class modelClass,
            final Map<Class, DataAccessObject> daos)
            throws InvalidAnnotationException {
        List<Field> childFields = annotationSearcher.searchClassForAnnotatedFields(PersistentChild.class, modelClass);
        Map<String, DataAccessObject> result = null;

        if (childFields.size() > 0) {
            result = new HashMap<>();
            for (Field child : childFields) {
                Class type = child.getType();
                DataAccessObject dao = daos.get(type);

                result.put(child.getName(), dao);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException {
        /* This is the primary method that will be called, therefore this will co-ordinate scanning
         * and building resources that will be registered to the relevant context.
         * */
        List<Class> annotatedModels = findAnnotatedModels();
        sortAnnotatedModels(annotatedModels);

        Map<Class, DataAccessObject> builtObjects = new HashMap<>();

        for (Class modelClass : annotatedModels) {
            DomainPersistenceBuilderConfig config = null;
            try {
                config = getDomainConfig(modelClass);
            } catch (MalformedPersistentModelException e) {
                LOG.error("FATAL: Unable to retrieve the domain persistence config", e);
                throw new RuntimeException("A fatal error occurred whilst trying to get the domain configuration for "
                        + modelClass.getSimpleName());
            }

            ReadableDataAccessComponent readable;
            try {
                readable = generateReadableDAC(config, modelClass);
            } catch (MalformedPersistentModelException e) {
                LOG.error("FATAL: Unable to generate readable DAC", e);
                throw new RuntimeException("A fatal error occurred whilst generating persistent framework.");
            }

            WritableDataAccessComponent writable;
            try {
                writable = generateWritableDAC(config, modelClass);
            } catch (MalformedPersistentModelException e) {
                LOG.error("FATAL: Unable to generate writable DAC", e);
                throw new RuntimeException("A fatal error occurred whilst generating persistent framework.");
            }

            // Create the child DAO mapping (if necessary)
            Map childDAOMapping;
            try {
                childDAOMapping = generateChildDAOMapping(modelClass, builtObjects);
            } catch (InvalidAnnotationException e) {
                LOG.error("FATAL: Error processing child entities", e);
                throw new RuntimeException("A fatal error occurred whilst generating child entity mapping.");
            }

            // Generate the Data Access Object
            DataAccessObject dao;
            if (writable != null) {
                dao = new DataAccessObjectImpl(childDAOMapping,
                        readable,
                        writable,
                        modelClass);
                builtObjects.put(modelClass, dao);

            } else {
                dao = new DataAccessObjectImpl(childDAOMapping,
                        readable,
                        modelClass);
                builtObjects.put(modelClass, dao);
            }
            String beanName = Character.toLowerCase(modelClass.getSimpleName().charAt(0)) + modelClass.getSimpleName().substring(1);
            beanFactory.registerSingleton(beanName, dao);
        }
    }
}
