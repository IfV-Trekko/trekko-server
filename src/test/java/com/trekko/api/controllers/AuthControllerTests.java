package com.trekko.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.bson.types.ObjectId;
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
import com.trekko.api.dtos.SignInRequestDto;
import com.trekko.api.dtos.SignUpRequestDto;
import com.trekko.api.interceptors.JwtAuthFilter;
import com.trekko.api.models.User;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.AuthUtils;
import com.trekko.api.utils.CustomUserDetails;

@WebMvcTest(AuthController.class)
public class AuthControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserRepository userRepository;

    @Mock
    private CustomUserDetails customUserDetails;
    @Mock
    private User user;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(this.userRepository))
                .build();
    }

    private void setupAuthentication() {
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
    public void testSignUpWithValidCredentials() throws Exception {
        when(this.userRepository.saveUser(any(User.class))).thenReturn(this.user);

        final var signUpRequestDto = new SignUpRequestDto("test@example.com", "ABcd56_.");

        when(this.userRepository.existsByEmail(anyString())).thenReturn(false);

        this.mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isCreated());

        verify(userRepository).saveUser(any(User.class));
    }

    @Test
    public void testSignUpWithInvalidEmail() throws Exception {
        final var signUpRequestDto = new SignUpRequestDto("invalid_email", "ABcd56_.");

        when(this.userRepository.existsByEmail(anyString())).thenReturn(false);

        this.mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSignUpWithInvalidPassword() throws Exception {
        final var signUpRequestDto = new SignUpRequestDto("test@example.com", "too_insecure");

        when(this.userRepository.existsByEmail(anyString())).thenReturn(false);

        this.mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signUpRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSignInWithValidCredentials() throws Exception {
        final String plainPassword = "ABcd56_.";

        when(this.userRepository.findUserByEmail(anyString())).thenReturn(this.user);
        when(this.user.getPasswordHash()).thenReturn(AuthUtils.hashPassword(plainPassword));
        when(this.user.getId()).thenReturn(new ObjectId());

        final var signInRequestDto = new SignInRequestDto("test@example.com", plainPassword);

        this.mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signInRequestDto)))
                .andExpect(status().isOk());

        verify(userRepository).findUserByEmail(anyString());
        verify(user).getPasswordHash();
        verify(user).getId();
    }

    @Test
    public void testSignInWithInvalidEmail() throws Exception {
        final var signInRequestDto = new SignInRequestDto("invalid_email", "ABcd56_.");

        when(this.userRepository.findUserByEmail(anyString())).thenReturn(null);

        this.mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signInRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSignInWithInvalidPassword() throws Exception {
        final var signInRequestDto = new SignInRequestDto("test@example.com", "invalid_password_ABcd56_.");

        when(this.userRepository.findUserByEmail(anyString())).thenReturn(this.user);
        when(this.user.getPasswordHash()).thenReturn(AuthUtils.hashPassword("different_password"));

        this.mockMvc.perform(post("/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(signInRequestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testForgotPassword() throws Exception {
        this.mockMvc.perform(post("/auth/forgot-password")).andExpect(status().isNotImplemented());
    }

    @Test
    public void testGetSession() throws Exception {
        this.setupAuthentication();
        this.mockMvc.perform(get("/auth/session")).andExpect(status().isOk());
    }
}
