package org.tp.SpringSecurity1Application.Services.impl;

import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tp.SpringSecurity1Application.Entites.User;
import org.tp.SpringSecurity1Application.Repositores.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(username);
        if (Objects.isNull(user)) {
            System.out.println("Email Id is invalid ");
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return new CustomUserDetails(user);
    }
}
