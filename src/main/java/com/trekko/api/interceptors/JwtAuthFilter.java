// package com.trekko.api.interceptors;

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
