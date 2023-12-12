package com.trekko.api.controllers;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.models.Trip;
import com.trekko.api.models.User;
import com.trekko.api.models.Vehicle;
import com.trekko.api.repositories.TripRepository;
import com.trekko.api.repositories.UserRepository;

@RestController
@RequestMapping("/trips")
public class TripsController {

  private final TripRepository tripRepository;
  private final UserRepository userRepository;

  @Autowired
  public TripsController(final TripRepository tripRepository, final UserRepository userRepository) {
    this.tripRepository = tripRepository;
    this.userRepository = userRepository;
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping
  public String getTrips() {
    // var trip = new Trip("uid", 0, 0, 1, null);
    // tripRepository.saveTrip(trip);
    // var trip = tripRepository.getFirst();
    // return trip.getUid();

    // userRepository.saveUser(user);

    // var user = new User("email@example.com", "test");

    // var trip = new Trip("uid", 0, 0, 1, new
    // HashSet<>(Arrays.asList(Vehicle.BIKE)), user, "My purpose", "My comment");
    // tripRepository.saveTrip(trip);
    // return trip.getUser();

    var user = this.userRepository.findUserByEmail("email@example.com");

    return user.getEmail();
  }
}
