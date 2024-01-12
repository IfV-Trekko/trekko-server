package com.trekko.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.dtos.RichTripDto;
import com.trekko.api.dtos.TripDto;
import com.trekko.api.repositories.TripRepository;
import com.trekko.api.utils.AuthUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/trips/{uid}")
public class TripController {

    private final TripRepository tripRepository;

    @Autowired
    public TripController(final TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getTrip(@PathVariable final String uid) {
        final var user = AuthUtils.getUserFromContext();
        final var trip = this.tripRepository.findTripByUid(uid, user);
        if (trip == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(RichTripDto.from(trip));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping
    public ResponseEntity<?> updateTrip(@PathVariable final String uid, @Valid @RequestBody final TripDto tripDto) {
        final var user = AuthUtils.getUserFromContext();
        final var trip = this.tripRepository.findTripByUid(uid, user);
        if (trip == null)
            return ResponseEntity.notFound().build();

        trip.updateFromDto(tripDto);
        this.tripRepository.saveTrip(trip);
        return ResponseEntity.ok().body(RichTripDto.from(trip));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity<?> deleteTrip(@PathVariable final String uid) {
        final var user = AuthUtils.getUserFromContext();
        final var trip = this.tripRepository.findTripByUid(uid, user);
        if (trip == null)
            return ResponseEntity.notFound().build();

        this.tripRepository.deleteTrip(trip);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
