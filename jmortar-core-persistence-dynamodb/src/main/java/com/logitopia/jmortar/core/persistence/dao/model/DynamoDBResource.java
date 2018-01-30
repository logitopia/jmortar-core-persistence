package com.logitopia.jmortar.core.persistence.dao.model;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;

/**
 * A dynamoDB-specific <tt>Resource</tt>.
 */
public class DynamoDBResource implements Resource {

    /**
     * The AWS credentials provider.
     */
    final AWSCredentialsProvider credentialsProvider;

    /**
     * The AWS Regions to persist on.
     */
    final Regions regions;

    /**
     * The number of concurrent threads to spawn during persistence.
     */
    final int noOfThreads;

    /**
     * Default Constructor. Create a DynamoDB-specific <tt>Resource</tt>.
     *
     * @param newCredentialsProvider
     * @param newRegions
     * @param newNoOfThreads
     */
    public DynamoDBResource(final AWSCredentialsProvider newCredentialsProvider,
            final Regions newRegions,
            final int newNoOfThreads) {
        credentialsProvider = newCredentialsProvider;
        regions = newRegions;
        noOfThreads = newNoOfThreads;
    }

    /**
     * Get the AWS Credentials Provider.
     *
     * @return The <tt>AWSCredentialsProvider</tt>
     */
    public AWSCredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    /**
     * Get the AWS Regions that we persist on.
     *
     * @return The AWS <tt>Regions</tt>.
     */
    public Regions getRegions() {
        return regions;
    }

    /**
     * Get the number of threads for running persistence.
     *
     * @return The Number of Threads.
     */
    public int getNoOfThreads() {
        return noOfThreads;
    }
}
