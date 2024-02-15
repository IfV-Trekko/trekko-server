package com.trekko.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

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
import com.trekko.api.dtos.TripDto;
import com.trekko.api.interceptors.JwtAuthFilter;
import com.trekko.api.models.TransportType;
import com.trekko.api.models.User;
import com.trekko.api.repositories.TripRepository;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.CustomUserDetails;

@WebMvcTest(TripsController.class)
public class TripsControllerTests {

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
    void testBatchAddTripsWithSingleTrip() throws Exception {
        when(this.tripRepository.existTripsWithUids(anyList(), any(User.class))).thenReturn(false);
        doNothing().when(this.tripRepository).saveTrips(anyList());

        final TripDto[] tripDtos = new TripDto[] {
                new TripDto("uid1", 1708010669467L, 1708010669667L, 20, Set.of(TransportType.BY_FOOT.name()))
        };

        this.mockMvc.perform(post("/trips/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(tripDtos)))
                .andExpect(status().isCreated());

        verify(this.tripRepository).existTripsWithUids(anyList(), any(User.class));
        verify(this.tripRepository).saveTrips(anyList());
    }

    @Test
    void testBatchAddTripsWithExistingTrip() throws Exception {
        when(this.tripRepository.existTripsWithUids(anyList(), any(User.class))).thenReturn(true);

        final TripDto[] tripDtos = new TripDto[] {
                new TripDto("uid1", 1708010669467L, 1708010669667L, 20, Set.of(TransportType.CAR.name()))
        };

        this.mockMvc.perform(post("/trips/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(tripDtos)))
                .andExpect(status().isConflict());

        verify(this.tripRepository).existTripsWithUids(anyList(), any(User.class));
    }

    @Test
    void testBatchAddTripsWithMultipleTrips() throws Exception {
        when(this.tripRepository.existTripsWithUids(anyList(), any(User.class))).thenReturn(false);
        doNothing().when(this.tripRepository).saveTrips(anyList());

        final TripDto[] tripDtos = new TripDto[] {
                new TripDto("uid1", 1708010669467L, 1708010669667L, 20, Set.of(TransportType.BY_FOOT.name())),
                new TripDto("uid2", 1708010669467L, 1708010669667L, 20,
                        Set.of(TransportType.BICYCLE.name(), TransportType.CAR.name()))
        };

        this.mockMvc.perform(post("/trips/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(tripDtos)))
                .andExpect(status().isCreated());

        verify(this.tripRepository).existTripsWithUids(anyList(), any(User.class));
        verify(this.tripRepository).saveTrips(anyList());
    }
}
