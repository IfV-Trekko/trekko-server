package com.trekko.api.models;

import java.util.Date;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trekko.api.utils.ObjectIdSerializer;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.PrePersist;
import dev.morphia.annotations.Property;

@Entity("users")
public class User {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private final ObjectId id;
    private final String email;
    private final String passwordHash;

    @Property("createdAt")
    private Date createdAt;
    @Property("updatedAt")
    private Date updatedAt;

    public User() {
        this.id = new ObjectId();
        this.email = null;
        this.passwordHash = null;
    }

    public User(final String email, final String passwordHash) {
        this.id = new ObjectId();
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    // public List<Trip> getTrips() {
    // return this.trips;
    // }

    // public void addTrip(final Trip trip) {
    // this.trips.add(trip);
    // trip.setUser(this);
    // }

    @PrePersist
    protected void prePersist() {
        final Date now = new Date();

        if (this.createdAt == null) {
            this.createdAt = now;
        }

        this.updatedAt = now;
    }
}
