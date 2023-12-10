package com.trekko.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.dtos.SignUpRequestDTO;
import com.trekko.api.models.User;
import com.trekko.api.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

  private final UserRepository userRepository;

  @Autowired
  public AuthController(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@Valid @RequestBody final SignUpRequestDTO signUpDTO,
      final BindingResult bindingResult) {
    System.out.println(signUpDTO.getEmail());
    System.out.println(signUpDTO.getPassword());

    final SignUpResponse signUpResponse = new SignUpResponse();

    return ResponseEntity.ok().body(signUpResponse);
  }
}

class SignUpResponse {

}
