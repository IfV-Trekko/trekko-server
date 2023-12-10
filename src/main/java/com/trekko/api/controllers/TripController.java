package com.trekko.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips/{uid}")
public class TripController {

  @GetMapping
  public String getTrip(@PathVariable final String uid) {
    return uid;
  }
}
