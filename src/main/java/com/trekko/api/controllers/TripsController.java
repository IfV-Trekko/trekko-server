package com.trekko.api.controllers;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.models.Trip;
import com.trekko.api.repositories.TripRepository;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.AuthUtils;

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
    final var user = AuthUtils.getUserFromContext();
    System.out.println(user);
    // var user = this.userRepository.findUserByEmail("email@example.com");

    // return user.getEmail();
    return user.getEmail();
  }
}
