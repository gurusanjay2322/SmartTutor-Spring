package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.SchoolAdminRequest;
import com.example.SmartTutor.dto.SchoolAdminResponse;
import com.example.SmartTutor.model.users.SchoolAdmin;
import com.example.SmartTutor.repository.SchoolAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final SchoolAdminRepository schoolAdminRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create-school-admin")
    public SchoolAdminResponse createSchoolAdmin(@RequestBody SchoolAdminRequest request) {
        if (schoolAdminRepository.existsByUsername(request.getUsername())) {
            return new SchoolAdminResponse("❌ Username already exists!", null);
        }

        SchoolAdmin schoolAdmin = new SchoolAdmin(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getSchoolName(),
                request.getLocation()
        );

        schoolAdminRepository.save(schoolAdmin);
        return new SchoolAdminResponse("✅ School Admin created successfully!", schoolAdmin.getId());
    }
}
