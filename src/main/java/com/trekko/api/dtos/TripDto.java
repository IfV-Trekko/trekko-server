package com.trekko.api.dtos;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trekko.api.annotations.ValidTransportTypeSet;
import com.trekko.api.models.Trip;
import com.trekko.api.models.TransportType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class TripDto {
    @NotNull
    @Size(min = 1, max = 255)
    private String uid;
    @Positive
    private long startTimestamp;
    @Positive
    private long endTimestamp;
    @Positive
    private double distance;

    @ValidTransportTypeSet
    private Set<String> transportTypes;

    @Size(max = 255)
    private String purpose;
    @Size(max = 255)
    private String comment;

    public TripDto() {
    }

    public TripDto(final String uid, final long startTimestamp, final long endTimestamp, final double distance,
            final Set<String> transportTypes) {
        this(uid, startTimestamp, endTimestamp, distance, transportTypes, null, null);
    }

    public TripDto(final String uid, final long startTimestamp, final long endTimestamp, final double distance,
            final Set<String> transportTypes, final String purpose, final String comment) {
        this.uid = uid;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.distance = distance;
        this.transportTypes = transportTypes;
        this.purpose = purpose;
        this.comment = comment;
    }

    public String getUid() {
        return this.uid;
    }

    public long getStartTimestamp() {
        return this.startTimestamp;
    }

    public long getEndTimestamp() {
        return this.endTimestamp;
    }

    public double getDistance() {
        return this.distance;
    }

    public Set<String> getTransportTypes() {
        return this.transportTypes;
    }

    @JsonIgnore
    public Set<TransportType> getTransportTypeSet() {
        final var transportTypes = new HashSet<TransportType>();

        for (final var transportType : this.transportTypes) {
            transportTypes.add(TransportType.from(transportType));
        }

        return transportTypes;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public String getComment() {
        return this.comment;
    }

    public static TripDto from(final Trip trip) {
        final var transportTypes = new HashSet<String>();

        for (final var transportType : trip.getTransportTypes()) {
            transportTypes.add(transportType.toString());
        }

        return new TripDto(trip.getUid(), trip.getStartTimestamp(), trip.getEndTimetamp(), trip.getDistance(),
                transportTypes, trip.getPurpose(), trip.getComment());
    }
}
