package com.trekko.api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.dtos.MetadataDto;

@RestController
@RequestMapping("/meta")
@Validated
public class MetaController {

  @Value("${metadata.name}")
  private String name;

  @Value("${metadata.terms}")
  private String terms;

  @GetMapping
  public ResponseEntity<?> getMetadata() {
    return ResponseEntity.ok().body(new MetadataDto(name, terms));
  }
}
