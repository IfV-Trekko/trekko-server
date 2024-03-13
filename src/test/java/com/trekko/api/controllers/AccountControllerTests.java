package com.trekko.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trekko.api.dtos.ConfirmEmailRequestDto;
import com.trekko.api.interceptors.JwtAuthFilter;
import com.trekko.api.models.User;
import com.trekko.api.repositories.TripRepository;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.AuthUtils;
import com.trekko.api.utils.CustomUserDetails;

@WebMvcTest(AccountController.class)
public class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private TripRepository tripRepository;
    @MockBean
    private UserRepository userRepository;

    @Mock
    private CustomUserDetails customUserDetails;
    @Mock
    private User user;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setupAuthentication() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .addFilters(new JwtAuthFilter(this.userRepository))
                .build();

        when(this.customUserDetails.getUser()).thenReturn(user);

        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());

        final SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetEmailConfirmationCode() throws Exception {
        when(this.user.isEmailConfirmed()).thenReturn(false);
        when(this.userRepository.saveUser(any(User.class))).thenReturn(this.user);

        this.mockMvc.perform(get("/account/email/confirmation"))
                .andExpect(status().is2xxSuccessful());

        verify(this.userRepository).saveUser(any(User.class));
    }

    @Test
    public void testGetEmailConfirmationCodeWhenUserIsAlreadyConfirmed() throws Exception {
        when(this.user.isEmailConfirmed()).thenReturn(true);

        this.mockMvc.perform(get("/account/email/confirmation"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testGetEmailConfirmationCodeWhenUserIsNotFound() throws Exception {
        when(this.customUserDetails.getUser()).thenReturn(null);

        this.mockMvc.perform(get("/account/email/confirmation"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testConfirmEmailWithValidCode() throws Exception {
        final String code = AuthUtils.generateEmailConfirmationCode();
        final ConfirmEmailRequestDto confirmEmailRequest = new ConfirmEmailRequestDto(code);

        when(this.user.isEmailConfirmed()).thenReturn(false);
        when(this.user.getEmailConfirmationCode()).thenReturn(code);
        when(this.userRepository.saveUser(any(User.class))).thenReturn(this.user);

        this.mockMvc.perform(post("/account/email/confirmation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(confirmEmailRequest)))
                .andExpect(status().is2xxSuccessful());

        verify(this.userRepository).saveUser(any(User.class));
    }

    @Test
    public void testConfirmEmailWithInvalidCode() throws Exception {
        final ConfirmEmailRequestDto confirmEmailRequest = new ConfirmEmailRequestDto("invalid_code");

        when(this.user.isEmailConfirmed()).thenReturn(false);
        when(this.user.getEmailConfirmationCode()).thenReturn("valid_code");

        this.mockMvc.perform(post("/account/email/confirmation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(confirmEmailRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testConfirmEmailWhenUserIsAlreadyConfirmed() throws Exception {
        final String code = AuthUtils.generateEmailConfirmationCode();
        final ConfirmEmailRequestDto confirmEmailRequest = new ConfirmEmailRequestDto(code);

        when(this.user.isEmailConfirmed()).thenReturn(true);

        this.mockMvc.perform(post("/account/email/confirmation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(confirmEmailRequest)))
                .andExpect(status().isConflict());

        verify(this.customUserDetails).getUser();
    }

    @Test
    public void testConfirmEmailWhenUserIsNotFound() throws Exception {
        final String code = AuthUtils.generateEmailConfirmationCode();
        final ConfirmEmailRequestDto confirmEmailRequest = new ConfirmEmailRequestDto(code);

        when(this.customUserDetails.getUser()).thenReturn(null);

        this.mockMvc.perform(post("/account/email/confirmation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(confirmEmailRequest)))
                .andExpect(status().isNotFound());

        verify(this.customUserDetails).getUser();
    }

    @Test
    public void testDeleteAccount() throws Exception {
        doNothing().when(this.tripRepository).deleteAllTripsByUser(this.user);
        doNothing().when(this.userRepository).deleteUser(this.user);

        this.mockMvc.perform(delete("/account"))
                .andExpect(status().is2xxSuccessful());

        verify(this.tripRepository).deleteAllTripsByUser(this.user);
        verify(this.userRepository).deleteUser(this.user);
    }

    @Test
    public void testDeleteAccountWhenUserIsNotFound() throws Exception {
        when(this.customUserDetails.getUser()).thenReturn(null);

        this.mockMvc.perform(delete("/account"))
                .andExpect(status().isBadRequest());
    }
}
