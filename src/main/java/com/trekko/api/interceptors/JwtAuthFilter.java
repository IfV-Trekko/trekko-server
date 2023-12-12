package com.trekko.api.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.trekko.api.models.User;
import com.trekko.api.repositories.UserRepository;
import com.trekko.api.utils.CustomUserDetails;
import com.trekko.api.utils.JwtUtil;

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

    // System.out.println("Token " + token);
    // System.out.println(JwtUtil.generateToken("657862079550312c939f8d5d"));
    final var isValid = JwtUtil.isTokenValid(token);
    if (!isValid || token == null) {
      filterChain.doFilter(req, res);
      return;
    }

    final var userId = JwtUtil.getUserFromToken(token);
    final var user = this.userRepository.findUserById(userId);
    if (user == null) {
      filterChain.doFilter(req, res);
      return;
    }

    if (token != null && user != null && isValid) {
      final Authentication auth = getAuthentication(user);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

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

  private Authentication getAuthentication(final User user) {
    final var userDetails = new CustomUserDetails(user);
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }
}

// import org.springframework.beans.factory.annotation.Autowired;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.trekko.api.models.User;
// import com.trekko.api.repositories.UserRepository;
// import com.trekko.api.utils.JwtUtil;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import java.io.IOException;

// public class JwtAuthFilter extends OncePerRequestFilter {

// @Autowired
// private UserRepository userRepository;

// @Override
// protected void doFilterInternal(final HttpServletRequest request, final
// HttpServletResponse response,
// final FilterChain filterChain)
// throws ServletException, IOException {
// final String token = request.getHeader("Authorization");
// if (token != null && token.startsWith("Bearer ")) {
// try {
// final String userId = JwtUtil.validateToken(token.substring(7));
// final User user = userRepository.findUserById(userId);
// if (user != null) {
// CustomUserDetails userDetails = new CustomUserDetails(user);
// SecurityContextHolder.getContext().setAuthentication(
// new UsernamePasswordAuthenticationToken(userDetails, null,
// userDetails.getAuthorities()));
// }
// } catch (Exception e) {
// // Handle invalid token case
// }
// }
// filterChain.doFilter(request, response);
// }
// }
