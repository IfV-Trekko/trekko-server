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
  private long distance;

  @Property("vehicles")
  private Set<Vehicle> vehicles = new HashSet<>();
  private String purpose;
  private String comment;

  @Property("createdAt")
  private Date createdAt;
  @Property("updatedAt")
  private Date updatedAt;

  public Trip(final String uid, final long startTimestamp, final long endTimetamp, final long distance,
      final Set<Vehicle> vehicles) {
    this(uid, startTimestamp, endTimetamp, distance, vehicles, null, null);
  }

  public Trip(final String uid, final long startTimestamp, final long endTimetamp, final long distance,
      final Set<Vehicle> vehicles,
      final String purpose, final String comment) {
    this.id = new ObjectId();
    this.uid = uid;
    this.startTimestamp = startTimestamp;
    this.endTimetamp = endTimetamp;
    this.distance = distance;
    this.vehicles = vehicles;
    this.purpose = purpose;
    this.comment = comment;
  }

  public ObjectId getId() {
    return this.id;
  }

  public String getUid() {
    return this.uid;
  }

  public long getStartTimestamp() {
    return this.startTimestamp;
  }

  public long getEndTimetamp() {
    return this.endTimetamp;
  }

  public long getDistance() {
    return this.distance;
  }

  public Set<Vehicle> getVehicles() {
    return this.vehicles;
  }

  public String getPurpose() {
    return this.purpose;
  }

  public String getComment() {
    return this.comment;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public Date getUpdatedAt() {
    return this.updatedAt;
  }

  @PrePersist
  protected void prePersist() {
    final Date now = new Date();

    if (this.createdAt == null) {
      this.createdAt = now;
    }

    this.updatedAt = now;
  }

  @Override
  public String toString() {
    return String.format("Trip[id=%s]", this.id);
  }
}
