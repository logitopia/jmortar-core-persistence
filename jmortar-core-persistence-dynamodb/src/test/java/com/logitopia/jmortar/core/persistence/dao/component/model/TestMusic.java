/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.logitopia.jmortar.core.persistence.annotation.Key;
import com.logitopia.jmortar.core.persistence.dao.annotation.DynamoDBPersistent;

/**
 * A Test Music model for DynamoDB testing.
 *
 * @author Stephen Cheesley
 */
@DynamoDBTable(tableName="TestMusic")
@DynamoDBPersistent
public class TestMusic {
  
  /**
   * The music item artist.
   */
  @Key
  private String artist;
  
  /**
   * The song title.
   */
  @Key
  private String songTitle;
  
  /**
   * The album title.
   */
  private String albumTitle;
  
  /**
   * The genre.
   */
  private String genre;

  @DynamoDBHashKey(attributeName="artist")
  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  @DynamoDBRangeKey(attributeName="songTitle")
  public String getSongTitle() {
    return songTitle;
  }

  public void setSongTitle(String songTitle) {
    this.songTitle = songTitle;
  }

  @DynamoDBAttribute(attributeName="albumTitle")
  public String getAlbumTitle() {
    return albumTitle;
  }

  public void setAlbumTitle(String albumTitle) {
    this.albumTitle = albumTitle;
  }

  @DynamoDBAttribute(attributeName="genre")
  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }
}
