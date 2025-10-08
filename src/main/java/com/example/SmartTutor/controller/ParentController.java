package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.ChildResponse;
import com.example.SmartTutor.dto.ParentProfileResponse;
import com.example.SmartTutor.dto.UpdateParentRequest;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Parent view own profile
    @GetMapping("/me/profile")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<ParentProfileResponse> getMyProfile(Authentication authentication) {
        String parentUsername = authentication.getName();

        Parent parent = parentRepository.findByUsername(parentUsername)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        List<ChildResponse> children = studentRepository.findByParentId(parent.getParentId())
                .stream()
                .map(s -> new ChildResponse(
                        s.getStudentId(),
                        s.getName(),
                        s.getClassLevel(),
                        s.getPoints()
                ))
                .toList();

        ParentProfileResponse response = new ParentProfileResponse(
                parent.getParentId(),
                parent.getName(),
                parent.getEmail(),
                parent.getUsername(),
                parent.getPhoneNumber(),
                children
        );

        return ResponseEntity.ok(response);
    }

    // ✅ Parent update own profile
    @PutMapping("/me/profile")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<Parent> updateMyProfile(
            Authentication authentication,
            @RequestBody UpdateParentRequest request) {

        String username = authentication.getName();
        Parent parent = parentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        parent.setName(request.getName());
        parent.setPhoneNumber(request.getPhoneNumber());
        parent.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            parent.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Parent updated = parentRepository.save(parent);
        return ResponseEntity.ok(updated);
    }
}
