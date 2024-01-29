package com.trekko.api.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

/**
 * Controller for handling profile-related requests.
 * Manages endpoints for viewing and updating user profiles.
 */
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;

    private final FormValidationService formValidationService = new FormValidationService();

    /**
     * Constructs a ProfileController with a specified UserRepository.
     *
     * @param userRepository The {@link UserRepository} to be used for user-related
     *                       operations.
     */
    @Autowired
    public ProfileController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the profile of the currently authenticated user.
     *
     * @return ResponseEntity with OK status and the user's profile in JSON format,
     *         or not found status with an error response DTO if the profile is not
     *         found.
     * @throws IOException if an error occurs during profile retrieval.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getProfile() throws IOException {
        final var user = AuthUtils.getUserFromContext();

        final String profile = user.getProfile();
        if (profile == null) {
            final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_PROFILE_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(profile);
    }

    /**
     * Updates the profile of the currently authenticated user. This method supports
     * partial updates of individual fields in the user's profile.
     * 
     * @param submittedFormJson The JSON string containing a selected subset of form
     *                          fields to be updated
     * @return ResponseEntity with OK status and the updated user's profile in JSON
     * @throws IOException if an error occurs during form validation or profile
     */
    @PreAuthorize("isAuthenticated()")
    @PatchMapping
    public ResponseEntity<?> updateForm(@RequestBody final String submittedFormJson) throws IOException {
        final String formTemplateJson = FormUtils.loadFormTemplate();

        final boolean isValidForm = this.formValidationService.validateFormData(formTemplateJson, submittedFormJson,
                false);
        if (!isValidForm) {
            final var errorResponse = new ErrorResponseDto(ResponseReason.FAILED_INVALID_FORM_DATA);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        final var user = AuthUtils.getUserFromContext();
        user.updateProfile(submittedFormJson);
        this.userRepository.saveUser(user);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user.getProfile());
    }

    /**
     * Submits and updates the profile of the currently authenticated user.
     *
     * @param submittedFormJson The JSON string representing the user's submitted
     *                          profile form.
     * @return ResponseEntity with created status if the profile is successfully
     *         updated, or bad request status with an error response DTO if the form
     *         data is invalid.
     * @throws IOException if an error occurs during form validation or profile
     *                     update.
     */
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

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(user.getProfile());
    }
}
