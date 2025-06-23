package org.tp.SpringSecurity1Application.Configes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final AuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/users/**").permitAll()  // Public endpoints
//                        .requestMatchers("/api/products/**").hasRole("EMPLOYEE")  // Employee only
                        .anyRequest().authenticated()  // All other endpoints need authentication
                )
                .csrf(csrf -> csrf.disable()) // Disable CSRF for API endpoints
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        //        UserDetails user1= User.withUsername("abc").password(passwordEncoder().encode("abc")).roles("USER").build();
////    UserDetails user2= User.withUsername("xyz").password(passwordEncoder().encode("xyz")).roles("USER").build();
////
////    return new InMemoryUserDetailsManager(user1,user2);
//        return username -> {
//            User user = userRepository.findByEmailId(username);
//            if (user == null) {
//                throw new UsernameNotFoundException("User not found with email: " + username);
//            }
//            return new org.springframework.security.core.userdetails.User(
//                    user.getEmailId(),
//                    user.getPassword(),
//                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
//            );
//        };
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
