package com.trekko.api.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

import com.trekko.api.interceptors.JwtAuthFilter;
import com.trekko.api.models.User;
import com.trekko.api.repositories.TripRepository;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.CustomUserDetails;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTests {

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
    public void testGetProfile() throws Exception {
        when(this.user.getProfile()).thenReturn("{}");

        this.mockMvc.perform(get("/profile")).andExpect(status().isOk());
    }

    @Test
    public void testGetProfileNotFound() throws Exception {
        when(this.user.getProfile()).thenReturn(null);

        this.mockMvc.perform(get("/profile")).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateFormValidData() throws Exception {
        final String validFormDataJson = "{ \"homeOffice\": false, \"gender\": \"male\", \"age\": 21, \"zip\": \"12345\" }";
        when(this.user.getProfile()).thenReturn(validFormDataJson);
        this.mockMvc.perform(patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validFormDataJson))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateFormInvalidData() throws Exception {
        final String invalidFormDataJson = "{\"invalid\":\"data\"}";
        this.mockMvc.perform(patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidFormDataJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSubmitProfileWithValidData() throws Exception {
        final String validProfileJson = "{ \"homeOffice\": false, \"gender\": \"male\", \"age\": 21, \"zip\": \"12345\" }";
        when(this.user.getProfile()).thenReturn(validProfileJson);
        this.mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validProfileJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void testSubmitProfileInvalidData() throws Exception {
        final String invalidProfileJson = "{\"invalid\":\"data\"}";
        this.mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidProfileJson))
                .andExpect(status().isBadRequest());
    }
}
