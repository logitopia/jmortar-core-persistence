/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.impl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.model.DynamoDBResource;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteDuplicateEntryException;
import com.logitopia.jmortar.core.persistence.exception.DataAccessWriteLockingException;
import org.apache.log4j.Logger;

/**
 * The <tt>WritableDynamoDBDataAccessComponentImpl</tt> class is an implementation of
 * <tt>WritableDataAccessComponent</tt> that uses AWS dynamo DB to store persistent data.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 * @param <T> The type of model that this component deals with.
 */
public final class WritableDynamoDBDataAccessComponentImpl<T>
        extends AbstractWritableDataAccessComponent<T, DynamoDBResource>
        implements WritableDataAccessComponent<T> {

  /**
   * The logger for this class.
   */
  public static final Logger LOG
          = Logger.getLogger(WritableDynamoDBDataAccessComponentImpl.class);

  /**
   * The credential provider that is used to create the dynamo DB client.
   */
  private AWSCredentialsProvider credentialProvider;

  /**
   * The region that the dynamo DB resides in.
   */
  private Regions awsRegion;

  /**
   * Default Constructor. Create a new writable DAC with the required AWS Credential provider.
   *
   * @param newResource The persistent resource payload.
   */
  public WritableDynamoDBDataAccessComponentImpl(final DynamoDBResource newResource) {
    super(newResource);
    credentialProvider = newResource.getCredentialsProvider();
    awsRegion = newResource.getRegions();
  }

  /**
   * Create an AWS dynamo client to allow for connection to amazon.
   *
   * @return The configured client.
   */
  private AmazonDynamoDBClient createClient() {
    return new AmazonDynamoDBClient(credentialProvider).withRegion(awsRegion);
  }

  /**
   * Save the model to the document database. This operation is generic so has been abstracted to a
   * private method to be run by both "save" and "update" operations.
   *
   * @param newModel The model to be saved.
   */
  private void saveModel(T newModel) {
    AmazonDynamoDBClient client = createClient();
    DynamoDBMapper mapper = new DynamoDBMapper(client);

    try {
      mapper.save(newModel);
    } catch (Exception all) {
      LOG.error("Issue during save", all);
    }
    
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(T newModel) throws DataAccessWriteLockingException,
          DataAccessWriteDuplicateEntryException {
    saveModel(newModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(T newModel) throws DataAccessWriteLockingException {
    saveModel(newModel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(T newModel) throws DataAccessWriteLockingException {
    AmazonDynamoDBClient client = createClient();
    DynamoDBMapper mapper = new DynamoDBMapper(client);

    mapper.delete(newModel);
  }

}
