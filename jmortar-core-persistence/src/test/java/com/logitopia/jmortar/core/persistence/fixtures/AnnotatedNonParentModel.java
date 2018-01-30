package com.logitopia.jmortar.core.persistence.fixtures;

import com.logitopia.jmortar.core.persistence.annotation.Key;

/**
 * A model with annotated fields that is not a parent model.
 */
public class AnnotatedNonParentModel {

    @Key
    final String name;

    /**
     * Default Constructor.
     *
     * @param newName The name
     */
    public AnnotatedNonParentModel(final String newName) {
        name = newName;
    }
}
