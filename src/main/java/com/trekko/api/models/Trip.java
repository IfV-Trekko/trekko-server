package com.trekko.api.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.PrePersist;
import dev.morphia.annotations.Property;

import org.bson.types.ObjectId;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity("trips")
public class Trip {

  @Id
  private final ObjectId id;
  private final String uid;

  private long startTimestamp;
  private long endTimetamp;

  @Property("vehicles")
  private Set<Vehicle> vehicles = new HashSet<>();
  private String purpose;
  private String comment;

  @Property("createdAt")
  private Date createdAt;
  @Property("updatedAt")
  private Date updatedAt;

  public Trip(final String uid, final long startTimestamp, final long endTimetamp, final Set<Vehicle> vehicles) {
    this(uid, startTimestamp, endTimetamp, vehicles, null, null);
  }

  public Trip(final String uid, final long startTimestamp, final long endTimetamp, final Set<Vehicle> vehicles,
      final String purpose, final String comment) {
    this.id = new ObjectId();
    this.uid = uid;
    this.startTimestamp = startTimestamp;
    this.endTimetamp = endTimetamp;
    this.vehicles = vehicles;
    this.purpose = purpose;
    this.comment = comment;
  }

  public ObjectId getId() {
    return id;
  }

  public String getUid() {
    return uid;
  }

  public long getStartTimestamp() {
    return startTimestamp;
  }

  public long getEndTimetamp() {
    return endTimetamp;
  }

  public Set<Vehicle> getVehicles() {
    return vehicles;
  }

  public String getPurpose() {
    return purpose;
  }

  public String getComment() {
    return comment;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  @PrePersist
  protected void prePersist() {
    final Date now = new Date();

    if (createdAt == null) {
      createdAt = now;
    }

    updatedAt = now;
  }

  @Override
  public String toString() {
    return String.format("Trip[id=%s]", id);
  }
}
