package com.trekko.api.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trekko.api.dtos.ErrorResponseDto;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.services.FormValidationService;
import com.trekko.api.utils.AuthUtils;
import com.trekko.api.utils.FormUtils;
import com.trekko.api.utils.ResponseReason;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;

    private final FormValidationService formValidationService = new FormValidationService();

    @Autowired
    public ProfileController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getProfile() throws IOException {
        final var user = AuthUtils.getUserFromContext();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user.getProfile());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<?> submitProfile(@RequestBody final String submittedFormJson) throws IOException {
        final String formTemplateJson = FormUtils.loadFormTemplate();

        final boolean isValidForm = this.formValidationService.validateFormData(formTemplateJson, submittedFormJson);
        if (!isValidForm) {
            final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_INVALID_FORM_DATA);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        final var user = AuthUtils.getUserFromContext();
        user.setProfile(submittedFormJson);
        this.userRepository.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
