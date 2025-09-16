package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.ChildResponse;
import com.example.SmartTutor.dto.CreateParentRequest;
import com.example.SmartTutor.dto.ParentProfileResponse;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Create a new parent (only School Admins allowed)
    @PostMapping("/create-parents")
    @PreAuthorize("hasRole('SCHOOL_ADMIN')")
    public ResponseEntity<Parent> createParent(@RequestBody CreateParentRequest request) {
        Parent parent = new Parent(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getPhoneNumber()
        );

        parent.setParentId(UUID.randomUUID().toString());
        Parent savedParent = parentRepository.save(parent);

        // Link parent to a student if studentId is provided
        if (request.getStudentId() != null) {
            Student student = studentRepository.findByStudentId(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            student.setParentId(savedParent.getParentId());
            studentRepository.save(student);
        }

        return ResponseEntity.ok(savedParent);
    }


    @GetMapping("/me/profile")
    public ResponseEntity<ParentProfileResponse> getMyProfile(Authentication authentication) {
        String parentUsername = authentication.getName();

        Parent parent = parentRepository.findByUsername(parentUsername)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        // Map students to safe ChildResponse DTO
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
}
