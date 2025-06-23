package org.tp.SpringSecurity1Application.Services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tp.SpringSecurity1Application.Dto.LoginDto;
import org.tp.SpringSecurity1Application.Dto.UserDto;
import org.tp.SpringSecurity1Application.Entites.User;
import org.tp.SpringSecurity1Application.Repositores.UserRepository;
import org.tp.SpringSecurity1Application.Services.UserService;
import org.tp.SpringSecurity1Application.Unit.JwtUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUnit jwtUnit;
    private final ModelMapper modelMapper;

    @Override
    public UserDto registerUser(UserDto userDto) {
        log.debug("Starting user registration process for: {}", userDto.getEmailId());
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("User registered successfully with ID: {}", savedUser.getId());
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public String loginUser(LoginDto loginDto) {
        log.debug("Attempting login for user: {}", loginDto.getEmailId());
        try {
            User dbUser = userRepository.findByEmailId(loginDto.getEmailId());
            if (dbUser == null) {
                log.warn("Login failed: Email not found - {}", loginDto.getEmailId());
                return "Login failed. Email not found.";
            }
            
            if (bCryptPasswordEncoder.matches(loginDto.getPassword(), dbUser.getPassword())) {
                String token = jwtUnit.generateToken(dbUser);
                log.info("Login successful for user: {}", loginDto.getEmailId());
                return "Bearer " + token;
            } else {
                log.warn("Login failed: Invalid password for user - {}", loginDto.getEmailId());
                return "Login failed. Invalid password.";
            }
        } catch (Exception e) {
            log.error("Login error for user {}: {}", loginDto.getEmailId(), e.getMessage(), e);
            return "Login failed. Error: " + e.getMessage();
        }
    }
}
