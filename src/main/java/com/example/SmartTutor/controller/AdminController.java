package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.*;
import com.example.SmartTutor.model.users.Admin;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.repository.AdminRepository;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---------------------------
    // Admin Profile
    // ---------------------------
    @GetMapping("/me/profile")
    public ResponseEntity<Admin> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        return ResponseEntity.ok(admin);
    }

    @PutMapping("/me/profile")
    public ResponseEntity<Admin> updateMyProfile(
            Authentication authentication,
            @RequestBody UpdateAdminRequest request) {

        String username = authentication.getName();
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setName(request.getName());
        admin.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Admin updated = adminRepository.save(admin);
        return ResponseEntity.ok(updated);
    }

    // ---------------------------
    // Manage Students (View only)
    // ---------------------------
    @GetMapping("/students/{studentId}/profile")
    public ResponseEntity<StudentProfileResponse> getStudentProfile(@PathVariable String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Parent parent = student.getParentId() != null
                ? parentRepository.findByParentId(student.getParentId()).orElse(null)
                : null;

        StudentProfileResponse profile = new StudentProfileResponse(
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getUsername(),
                student.getPoints(),
                parent != null ? parent.getName() : null,
                parent != null ? parent.getPhoneNumber() : student.getParentPhoneNumber()
        );

        return ResponseEntity.ok(profile);
    }

    // ---------------------------
    // Manage Parents (View only)
    // ---------------------------
    @GetMapping("/parents/{parentId}/profile")
    public ResponseEntity<Parent> getParentProfile(@PathVariable String parentId) {
        Parent parent = parentRepository.findByParentId(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        return ResponseEntity.ok(parent);
    }
}
