package com.trekko.api.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.PrePersist;
import dev.morphia.annotations.Property;
import dev.morphia.annotations.Reference;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trekko.api.dtos.TripDto;
import com.trekko.api.utils.ObjectIdSerializer;

import java.util.HashSet;
import java.util.Set;
import java.util.Date;

@Entity("trips")
public class Trip {

    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String uid;

    private long startTimestamp;
    private long endTimetamp;
    private double distance;

    @Property("transportTypes")
    private Set<TransportType> transportTypes = new HashSet<>();
    private String purpose;
    private String comment;

    @Reference
    private User user;

    @Property("createdAt")
    private Date createdAt;
    @Property("updatedAt")
    private Date updatedAt;

    public Trip() {
        this(null, 0, 0, 0, new HashSet<>(), null);
    }

    public Trip(final String uid, final long startTimestamp, final long endTimetamp, final double distance,
            final Set<TransportType> transportTypes, final User user) {
        this(uid, startTimestamp, endTimetamp, distance, transportTypes, user, null, null);
    }

    public Trip(final String uid, final long startTimestamp, final long endTimetamp, final double distance,
            final Set<TransportType> transportTypes, final User user, final String purpose, final String comment) {
        this.id = new ObjectId();
        this.uid = uid;
        this.startTimestamp = startTimestamp;
        this.endTimetamp = endTimetamp;
        this.distance = distance;
        this.transportTypes = transportTypes;
        this.user = user;
        this.purpose = purpose;
        this.comment = comment;
    }

    public ObjectId getId() {
        return this.id;
    }

    public void setId(final ObjectId id) {
        this.id = id;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }

    public long getStartTimestamp() {
        return this.startTimestamp;
    }

    public long getEndTimetamp() {
        return this.endTimetamp;
    }

    public double getDistance() {
        return this.distance;
    }

    public Set<TransportType> getTransportTypes() {
        return this.transportTypes;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(final User user) {
        this.user = user;
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

    public void updateFromDto(final TripDto tripDto) {
        this.startTimestamp = tripDto.getStartTimestamp();
        this.endTimetamp = tripDto.getEndTimestamp();
        this.distance = tripDto.getDistance();
        this.transportTypes = tripDto.getTransportTypeSet();
        this.purpose = tripDto.getPurpose();
        this.comment = tripDto.getComment();
    }

    @Override
    public String toString() {
        return String.format("Trip[id=%s]", this.id);
    }

    public static Trip fromDto(final TripDto tripDto, final User user) {
        return new Trip(tripDto.getUid(), tripDto.getStartTimestamp(), tripDto.getEndTimestamp(),
                tripDto.getDistance(), tripDto.getTransportTypeSet(), user, tripDto.getPurpose(), tripDto.getComment());
    }
}
