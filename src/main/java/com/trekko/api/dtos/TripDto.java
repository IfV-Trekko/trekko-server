package com.trekko.api.dtos;

import java.util.HashSet;
import java.util.Set;

import com.trekko.api.annotations.ValidVehicleSet;
import com.trekko.api.models.Trip;
import com.trekko.api.models.Vehicle;

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

    @ValidVehicleSet
    private Set<String> vehicles;

    @Size(max = 255)
    private String purpose;
    @Size(max = 255)
    private String comment;

    public TripDto() {
    }

    public TripDto(final String uid, final long startTimestamp, final long endTimestamp, final double distance,
            final Set<String> vehicles) {
        this(uid, startTimestamp, endTimestamp, distance, vehicles, null, null);
    }

    public TripDto(final String uid, final long startTimestamp, final long endTimestamp, final double distance,
            final Set<String> vehicles, final String purpose, final String comment) {
        this.uid = uid;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.distance = distance;
        this.vehicles = vehicles;
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

    public Set<String> getVehicles() {
        return this.vehicles;
    }

    public Set<Vehicle> getVehicleSet() {
        final var vehicles = new HashSet<Vehicle>();

        for (final var vehicle : this.vehicles) {
            vehicles.add(Vehicle.from(vehicle));
        }

        return vehicles;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public String getComment() {
        return this.comment;
    }

    public static TripDto from(final Trip trip) {
        final var vehicles = new HashSet<String>();

        for (final var vehicle : trip.getVehicles()) {
            vehicles.add(vehicle.toString());
        }

        return new TripDto(trip.getUid(), trip.getStartTimestamp(), trip.getEndTimetamp(), trip.getDistance(),
                vehicles, trip.getPurpose(), trip.getComment());
    }
}
