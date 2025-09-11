package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.LoginRequest;
import com.example.SmartTutor.dto.LoginResponse;
import com.example.SmartTutor.model.User;
import com.example.SmartTutor.repository.UserRepository;
import com.example.SmartTutor.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
                long expiresIn = 3600; // 1 hour in seconds (matches your JWT expiry)
                return new LoginResponse(user.getUsername(), user.getRole().name(), token, expiresIn);
            }
        }
        throw new RuntimeException("Invalid credentials!");
    }
}
