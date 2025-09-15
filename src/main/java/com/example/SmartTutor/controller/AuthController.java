package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.LoginRequest;
import com.example.SmartTutor.dto.LoginResponse;
import com.example.SmartTutor.model.User;
import com.example.SmartTutor.model.users.SchoolAdmin;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.repository.UserRepository;
import com.example.SmartTutor.repository.SchoolAdminRepository;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.StudentRepository;
import com.example.SmartTutor.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository; // if you still have generic users
    private final SchoolAdminRepository schoolAdminRepository;
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Optional<? extends User> userOpt = Optional.empty();

        // check in generic users
        userOpt = userRepository.findByUsername(request.getUsername());

        // if not found, check in schooladmins
        if (userOpt.isEmpty()) {
            userOpt = schoolAdminRepository.findByUsername(request.getUsername());
        }

        // if not found, check in parents
        if (userOpt.isEmpty()) {
            userOpt = parentRepository.findByUsername(request.getUsername());
        }

        // if not found, check in students
        if (userOpt.isEmpty()) {
            userOpt = studentRepository.findByUsername(request.getUsername());
        }

        // still not found? invalid login
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid credentials!");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        long expiresIn = 3600; // 1 hour
        return new LoginResponse(user.getUsername(), user.getRole().name(), token, expiresIn);
    }
}
