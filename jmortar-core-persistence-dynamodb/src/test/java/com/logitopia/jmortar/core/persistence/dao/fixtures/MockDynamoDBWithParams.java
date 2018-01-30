package com.logitopia.jmortar.core.persistence.dao.fixtures;

import com.logitopia.jmortar.core.persistence.annotation.Ignore;
import com.logitopia.jmortar.core.persistence.dao.annotation.DynamoDBPersistent;

/**
 * A mock model create to test reading an annotated model with parameters.
 */
@Ignore
@DynamoDBPersistent(
        credentialsProvider = "testCredentialsProvider",
        regions = "testRegions",
        noOfThreads = 3
)
public class MockDynamoDBWithParams {
}
