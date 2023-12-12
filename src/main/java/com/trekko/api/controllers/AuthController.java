package com.trekko.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.config.ResponseCodec;
import com.trekko.api.dtos.ErrorResponseDTO;
import com.trekko.api.dtos.SignInRequestDTO;
import com.trekko.api.dtos.SignUpRequestDTO;
import com.trekko.api.dtos.SignUpResponseDTO;
import com.trekko.api.models.User;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.JwtUtil;

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
    final String userEmail = signUpDTO.getEmail();

    if (userRepository.existsByEmail(userEmail)) {
      return ResponseEntity.badRequest().body(new ErrorResponseDTO(ResponseCodec.FAILED_EMAIL_ALREADY_IN_USE));
    }

    final String passwordHash = this.hashPassword(signUpDTO.getPassword());

    final var user = new User(userEmail, passwordHash);
    userRepository.saveUser(user);

    final var signUpResponse = new SignUpResponseDTO();

    return ResponseEntity.ok().body(signUpResponse);
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signIn(@Valid @RequestBody final SignInRequestDTO signInDTO,
      final BindingResult bindingResult) {
    final var user = userRepository.findUserByEmail(signInDTO.getEmail());
    if (user == null)
      return ResponseEntity.badRequest().body(new ErrorResponseDTO(ResponseCodec.FAILED_USER_NOT_FOUND));

    final boolean isPasswordValid = BCrypt.checkpw(signInDTO.getPassword(), user.getPasswordHash());
    if (!isPasswordValid)
      return ResponseEntity.badRequest().body(new ErrorResponseDTO(ResponseCodec.FAILED_INVALID_CREDENTIALS));

    final String token = JwtUtil.generateToken(user.getId().toString());

    return ResponseEntity.ok().body("Success. Token: " + token);
  }

  private String hashPassword(final String plainPassword) {
    final var passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.encode(plainPassword);
  }
}
