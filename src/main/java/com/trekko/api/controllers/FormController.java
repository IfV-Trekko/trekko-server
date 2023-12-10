package com.trekko.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/form")
public class FormController {

  @PostMapping
  public String getForm(@Valid @RequestBody final TestDTO testDTO) {
    return "Form";
  }
}

class TestDTO {
  @NotBlank(message = "FAILED_A_REQUIRED")
  private String a;
}
