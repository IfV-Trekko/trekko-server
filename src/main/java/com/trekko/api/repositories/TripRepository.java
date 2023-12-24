package com.trekko.api.repositories;

import dev.morphia.Datastore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.bson.types.ObjectId;

import com.trekko.api.models.Trip;
import com.trekko.api.models.User;

@Repository
public class TripRepository {

    private final Datastore datastore;

    @Autowired
    public TripRepository(final Datastore datastore) {
        this.datastore = datastore;
    }

    public Trip saveTrip(final Trip trip) {
        return this.datastore.save(trip);
    }

    public void saveTrips(final List<Trip> trips) {
        this.datastore.save(trips);
    }

    public Trip findTripById(final ObjectId id) {
        return this.datastore.find(Trip.class).stream().filter(trip -> trip.getId().equals(id)).findFirst()
                .orElse(null);
    }

    public Trip findTripByUid(final String uid, final User user) {
        return this.datastore.find(Trip.class).stream()
                .filter(trip -> trip.getUid().equals(uid) && trip.getUser().getId().equals(user.getId())).findFirst()
                .orElse(null);
    }

    public boolean existsByUid(final String uid, final User user) {
        return this.datastore.find(Trip.class).stream()
                .anyMatch(trip -> trip.getUid().equals(uid) && trip.getUser().getId().equals(user.getId()));
    }

    public void deleteTrip(final Trip trip) {
        this.datastore.delete(trip);
    }

    public Trip getFirst() {
        return this.datastore.find(Trip.class).first();
    }

    public long getCount() {
        return this.datastore.find(Trip.class).count();
    }
}
