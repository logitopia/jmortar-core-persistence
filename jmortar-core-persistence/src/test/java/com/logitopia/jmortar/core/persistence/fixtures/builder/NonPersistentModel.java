package com.logitopia.jmortar.core.persistence.fixtures.builder;

import com.logitopia.jmortar.core.persistence.annotation.Key;
import com.logitopia.jmortar.core.persistence.fixtures.builder.annotation.InventedNonPersistent;

/**
 * A model that has an annotation that is NOT persistent.
 */
@InventedNonPersistent
public class NonPersistentModel {

    @Key
    String id;

    String name;

    public NonPersistentModel(final String id,
            final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
