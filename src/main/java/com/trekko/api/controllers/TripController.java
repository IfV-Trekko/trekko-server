package com.trekko.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.dtos.RichTripDto;
import com.trekko.api.repositories.TripRepository;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.AuthUtils;

@RestController
@RequestMapping("/trips/{uid}")
public class TripController {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    @Autowired
    public TripController(final TripRepository tripRepository, final UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
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
    public ResponseEntity<?> updateTrip(@PathVariable final String uid) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
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
