package com.trekko.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.models.Trip;
import com.trekko.api.repositories.TripRepository;

@RestController
@RequestMapping("/trips")
public class TripsController {

  private final TripRepository tripRepository;

  @Autowired
  public TripsController(final TripRepository tripRepository) {
    this.tripRepository = tripRepository;
  }

  @GetMapping
  public String getTrips() {
    var trip = new Trip("uid", 0, 0, 1, null);
    tripRepository.saveTrip(trip);
    // var trip = tripRepository.getFirst();
    return trip.getUid();
  }
}
