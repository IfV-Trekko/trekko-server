package com.trekko.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.trekko.api.interceptors.JwtAuthFilter;
import com.trekko.api.repositories.UserRepository;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class AuthConfig {

    private final UserRepository userRepository;

    @Autowired
    public AuthConfig(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthFilter(this.userRepository), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handler -> handler.disable())
                .build();
    }
}
