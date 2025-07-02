package org.tp.SpringSecurity1Application.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tp.SpringSecurity1Application.Dto.ApiResponce;
import org.tp.SpringSecurity1Application.Dto.LoginDto;
import org.tp.SpringSecurity1Application.Dto.UserDto;
import org.tp.SpringSecurity1Application.Services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponce> register(@Valid @RequestBody UserDto userDto) {
        log.info("Received registration request for user: {}", userDto.getEmailId());
        UserDto registeredUser = userService.registerUser(userDto);
        ApiResponce apiResponce = new ApiResponce();
        apiResponce.setData(registeredUser);
        apiResponce.setMessage("User Register success role is " + registeredUser.getRole());
        apiResponce.setStatus(HttpStatus.OK);
        log.info("User registered successfully: {}", registeredUser.getEmailId());
        return ResponseEntity.ok(apiResponce);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponce> login(@Valid @RequestBody LoginDto loginDto) {
        log.info("Received login request for user: {}", loginDto.getEmailId());
        String token = userService.loginUser(loginDto);
        ApiResponce apiResponce = new ApiResponce();
        apiResponce.setData(loginDto.getEmailId());
        apiResponce.setMessage(token);
        apiResponce.setStatus(HttpStatus.OK);
        log.info("User logged in successfully: {}", loginDto.getEmailId());
        return ResponseEntity.ok(apiResponce);
    }

    @GetMapping
    public Object getUrl(HttpServletRequest request) {
        log.debug("Retrieving CSRF token");
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (token.chars().filter(ch -> ch == '.').count() == 2) {
                return request.getAttribute("_csrf");
            } else {
                log.warn("Invalid JWT format received: {}", token);
                return null;
            }
        }
        return null;
    }
}
