/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.model.Resource;

/**
 * An abstract implementation of the <tt>WritableDataAccessComponent</tt>
 *
 * @param <T>  The type of model that this component is dealing with.
 * @param <T1> The type of domain resource that will be used by this DAC.
 * @author Stephen Cheesley
 */
public abstract class AbstractWritableDataAccessComponent<T, T1 extends Resource>
        extends AbstractDataAccessComponent<T1>
        implements WritableDataAccessComponent<T> {

    /**
     * Default Constructor. Create a writable data access component using the domain resource.
     *
     * @param newResource The domain <tt>Resource</tt>.
     */
    public AbstractWritableDataAccessComponent(T1 newResource) {
        super(newResource);
    }
}
