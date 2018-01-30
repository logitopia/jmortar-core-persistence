package com.logitopia.jmortar.core.persistence.fixtures.builder;

import com.logitopia.jmortar.core.persistence.annotation.Key;
import com.logitopia.jmortar.core.persistence.fixtures.builder.annotation.InventedPersistent;

/**
 * A basic persistent model for testing.
 */
@InventedPersistent(
        credentialsProvider = "testCredProv"
)
public class BasicModel {

    @Key
    String id;

    String name;

    public BasicModel(final String id,
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
