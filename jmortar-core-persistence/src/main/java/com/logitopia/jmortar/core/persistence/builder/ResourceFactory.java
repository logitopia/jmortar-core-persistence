/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.builder;

import com.logitopia.jmortar.core.persistence.builder.exception.MalformedPersistentModelException;
import com.logitopia.jmortar.core.persistence.dao.model.Resource;

/**
 * The <tt>ResourceFactory</tt> will create a resource that can be used to by DAC to perform
 * persistence.
 *
 * @param <T> The Resource that the given factory will return.
 * @author Stephen Cheesley
 */
public interface ResourceFactory<T extends Resource> {

    /**
     * Build the required resource based on the annotated model.
     *
     * @param model The model class to build persistent resource for
     * @return A built <tt>Resource</tt>
     *
     * @throws MalformedPersistentModelException
     */
    T build(Class model) throws MalformedPersistentModelException;
}
