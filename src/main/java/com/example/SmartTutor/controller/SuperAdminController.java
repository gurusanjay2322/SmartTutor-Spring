package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.SchoolAdminRequest;
import com.example.SmartTutor.dto.SchoolAdminResponse;
import com.example.SmartTutor.model.Role;
import com.example.SmartTutor.model.User;
import com.example.SmartTutor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create-school-admin")
    public SchoolAdminResponse createSchoolAdmin(@RequestBody SchoolAdminRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new SchoolAdminResponse("❌ Username already exists!", null);
        }

        User schoolAdmin = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.SCHOOL_ADMIN)
                .build();

        userRepository.save(schoolAdmin);
        return new SchoolAdminResponse("✅ School Admin created successfully!", schoolAdmin.getId());
    }
}
