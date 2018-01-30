package com.logitopia.jmortar.core.persistence.fixtures;

import com.logitopia.jmortar.core.persistence.annotation.PersistentChild;

/**
 * A model for testing that has a persistent child member.
 */
public class ParentModel {

    @PersistentChild
    final String name;

    /**
     * Default Constructor.
     *
     * @param newName The name
     */
    public ParentModel(String newName) {
        name = newName;
    }
}
