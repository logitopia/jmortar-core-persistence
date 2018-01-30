package com.logitopia.jmortar.core.persistence.fixtures.builder;

import com.logitopia.jmortar.core.persistence.builder.ResourceFactory;
import com.logitopia.jmortar.core.persistence.builder.exception.MalformedPersistentModelException;
import com.logitopia.jmortar.core.persistence.fixtures.builder.dao.InventedResource;

public class InventedResourceFactory implements ResourceFactory<InventedResource> {

    /**
     * The test resource
     */
    private InventedResource resource;

    /**
     * Default Constructor.
     *
     * @param newResource
     */
    public InventedResourceFactory(final InventedResource newResource) {
        resource = newResource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InventedResource build(Class model) throws MalformedPersistentModelException {
        return resource;
    }
}
