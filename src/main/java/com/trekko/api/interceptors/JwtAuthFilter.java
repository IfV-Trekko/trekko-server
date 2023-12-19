package com.trekko.api.interceptors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.trekko.api.models.User;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.CustomUserDetails;
import com.trekko.api.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class JwtAuthFilter extends GenericFilterBean {

    private final UserRepository userRepository;

    public JwtAuthFilter(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain filterChain)
            throws IOException, ServletException {
        final var request = (HttpServletRequest) req;
        final String token = this.extractToken(request);

        final var isValid = JwtUtils.isTokenValid(token);
        if (!isValid || token == null) {
            filterChain.doFilter(req, res);
            return;
        }

        final var userId = JwtUtils.getUserIdFromToken(token);
        final var user = this.userRepository.findUserById(userId);
        if (user == null) {
            filterChain.doFilter(req, res);
            return;
        }

        final Authentication auth = buildAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(req, res);
    }

    private String extractToken(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        } else {
            return null;
        }
    }

    private Authentication buildAuthentication(final User user) {
        final var userDetails = new CustomUserDetails(user);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
