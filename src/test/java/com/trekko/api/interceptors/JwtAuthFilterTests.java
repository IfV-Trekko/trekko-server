package com.trekko.api.interceptors;

import com.trekko.api.models.User;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.JwtUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthFilterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthFilter jwtAuthFilter;

    private MockedStatic<JwtUtils> mockedJwtUtils;

    @BeforeEach
    void setUp() {
        mockedJwtUtils = mockStatic(JwtUtils.class);
    }

    @AfterEach
    void tearDown() {
        mockedJwtUtils.close();
        SecurityContextHolder.clearContext();
    }

    @Test
    void whenTokenIsValid_thenAuthenticateUser() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");

        mockedJwtUtils.when(() -> JwtUtils.isTokenValid("validToken")).thenReturn(true);
        mockedJwtUtils.when(() -> JwtUtils.getUserIdFromToken("validToken")).thenReturn("userId");

        User mockUser = new User();
        when(userRepository.findUserById("userId")).thenReturn(mockUser);

        jwtAuthFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);

        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        assertNotNull(authentication);
    }

    @Test
    void whenTokenIsInvalid_thenDoFilterWithoutAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");

        mockedJwtUtils.when(() -> JwtUtils.isTokenValid("invalidToken")).thenReturn(false);

        jwtAuthFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
