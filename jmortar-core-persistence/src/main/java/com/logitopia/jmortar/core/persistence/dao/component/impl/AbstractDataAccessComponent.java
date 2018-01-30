/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.logitopia.jmortar.core.persistence.dao.model.Resource;

/**
 * An abstract implementation that manages resources for both types of Data Access Component.
 *
 * @param T The domain type of the <tt>Resource</tt> that we want to manage.
 * @author Stephen Cheesley
 */
public abstract class AbstractDataAccessComponent<T extends Resource> {

    /**
     * The data access resource.
     */
    private T resource;

    /**
     * Default Constructor. Create the DataAccessComponent with the required resource.
     *
     * @param newResource
     */
    public AbstractDataAccessComponent(final T newResource) {
        resource = newResource;
    }

    /**
     * Get the data access resource.
     *
     * @return the <tt>Resource</tt>.
     */
    public T getResource() {
        return resource;
    }
}
