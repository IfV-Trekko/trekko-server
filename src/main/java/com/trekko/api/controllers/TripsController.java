package com.trekko.api.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.dtos.ErrorResponseDto;
import com.trekko.api.dtos.RichTripDto;
import com.trekko.api.dtos.TripDto;
import com.trekko.api.models.Trip;
import com.trekko.api.repositories.TripRepository;
import com.trekko.api.utils.AuthUtils;
import com.trekko.api.utils.ResponseReason;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/trips")
public class TripsController {

    private final TripRepository tripRepository;

    @Autowired
    public TripsController(final TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getTrips() {
        final var user = AuthUtils.getUserFromContext();
        final var trips = this.tripRepository.getTripsByUser(user);
        return ResponseEntity.ok(trips.stream().map(RichTripDto::from).collect(Collectors.toList()));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> addTrip(@Valid @RequestBody final TripDto tripDto) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/batch")
    public ResponseEntity<?> addTrips(@Valid @RequestBody final TripDto[] tripDtos) {
        final var user = AuthUtils.getUserFromContext();

        // check if any of the trips already exist
        final List<String> tripsUids = Arrays.stream(tripDtos).map(TripDto::getUid).collect(Collectors.toList());
        if (this.tripRepository.existTripsWithUids(tripsUids, user)) {
            final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_RESOURCE_CONFLICT);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        final List<Trip> trips = Arrays.stream(tripDtos).map(tripDto -> Trip.fromDto(tripDto, user))
                .collect(Collectors.toList());
        this.tripRepository.saveTrips(trips);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trips.stream().map(RichTripDto::from).collect(Collectors.toList()));
    }
}
