package com.trekko.api.dtos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.trekko.api.models.Trip;
import com.trekko.api.utils.ObjectIdSerializer;

public class RichTripDto extends TripDto {
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private Date createdAt;
    private Date updatedAt;

    public RichTripDto(final ObjectId id, final String uid, final long startTimestamp, final long endTimestamp,
            final double distance, final Set<String> transportTypes, final String purpose, final String comment,
            final Date createdAt, final Date updatedAt) {
        super(uid, startTimestamp, endTimestamp, distance, transportTypes, purpose, comment);

        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ObjectId getId() {
        return this.id;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public static RichTripDto from(final Trip trip) {
        final var transportTypes = new HashSet<String>();

        for (final var transportType : trip.getTransportTypes()) {
            transportTypes.add(transportType.toString());
        }

        return new RichTripDto(trip.getId(), trip.getUid(), trip.getStartTimestamp(), trip.getEndTimetamp(),
                trip.getDistance(),
                transportTypes, trip.getPurpose(), trip.getComment(), trip.getCreatedAt(), trip.getUpdatedAt());
    }
}
