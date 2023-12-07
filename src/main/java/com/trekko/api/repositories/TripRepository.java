package com.trekko.api.repositories;

import dev.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;

import com.trekko.api.models.Trip;

@Repository
public class TripRepository {

  private final Datastore datastore;

  @Autowired
  public TripRepository(final Datastore datastore) {
    this.datastore = datastore;
  }

  public void saveTrip(final Trip trip) {
    datastore.save(trip);
  }

  public Trip findTripById(final ObjectId id) {
    return datastore.find(Trip.class).stream().filter(trip -> trip.getId().equals(id)).findFirst().orElse(null);
  }
}
