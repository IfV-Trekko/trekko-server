package com.trekko.api.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("trips")
public class Trip {

  @Id
  private ObjectId id;

  private String comment;

  public ObjectId getId() {
    return id;
  }
}
