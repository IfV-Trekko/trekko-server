package com.trekko.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.repositories.TripRepository;
import com.trekko.api.repositories.UserRepository;

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

  @GetMapping
  public String getTrip(@PathVariable final String uid) {
    return uid;
  }
}
