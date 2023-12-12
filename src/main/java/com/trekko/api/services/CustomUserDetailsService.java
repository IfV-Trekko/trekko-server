// package com.trekko.api.services;

// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import
// org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;

// import com.trekko.api.models.User;

// @Service
// public class CustomUserDetailsService implements UserDetailsService {
// @Override
// public UserDetails loadUserByUsername(final String username) throws
// UsernameNotFoundException {
// return null;
// }

// public UserDetails loadByUserId(final String id) throws
// UsernameNotFoundException {
// return this.buildUserDetails(null)
// }

// private UserDetails buildUserDetails(final User user) {
// if (user == null)
// return null;

// return org.springframework.security.core.userdetails.User
// .withUsername(user.getId().toString())
// .password(user.getPasswordHash())
// .build();
// }
// }
