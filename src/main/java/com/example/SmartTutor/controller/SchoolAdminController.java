package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.UpdateParentRequest;
import com.example.SmartTutor.dto.UpdateSchoolAdminRequest;
import com.example.SmartTutor.dto.UpdateStudentRequest;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.model.users.SchoolAdmin;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.SchoolAdminRepository;
import com.example.SmartTutor.repository.StudentRepository;
import com.example.SmartTutor.dto.StudentProfileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schooladmin")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")
public class SchoolAdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private SchoolAdminRepository schoolAdminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---------------------------
    // SchoolAdmin profile endpoints
    // ---------------------------
    @GetMapping("/me/profile")
    public ResponseEntity<SchoolAdmin> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        SchoolAdmin admin = schoolAdminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("SchoolAdmin not found"));
        return ResponseEntity.ok(admin);
    }

    @PutMapping("/me/profile")
    public ResponseEntity<SchoolAdmin> updateMyProfile(
            Authentication authentication,
            @RequestBody UpdateSchoolAdminRequest request) {

        String username = authentication.getName();
        SchoolAdmin admin = schoolAdminRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("SchoolAdmin not found"));

        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setSchoolName(request.getSchoolName());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        SchoolAdmin updated = schoolAdminRepository.save(admin);
        return ResponseEntity.ok(updated);
    }

    // ---------------------------
    // Student endpoints
    // ---------------------------
    @GetMapping("/students/{studentId}/profile")
    public ResponseEntity<StudentProfileResponse> getStudentProfile(@PathVariable String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Parent parent = null;
        if (student.getParentId() != null) {
            parent = parentRepository.findByParentId(student.getParentId()).orElse(null);
        }

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

    @PutMapping("/students/{studentId}/profile")
    public ResponseEntity<Student> updateStudentProfile(
            @PathVariable String studentId,
            @RequestBody UpdateStudentRequest request) {

        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setClassLevel(request.getClassLevel());
        student.setParentPhoneNumber(request.getParentPhoneNumber());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            student.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Student updated = studentRepository.save(student);
        return ResponseEntity.ok(updated);
    }

    // ---------------------------
    // Parent endpoints
    // ---------------------------
    @GetMapping("/parents/{parentId}/profile")
    public ResponseEntity<Parent> getParentProfile(@PathVariable String parentId) {
        Parent parent = parentRepository.findByParentId(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        return ResponseEntity.ok(parent);
    }

    @PutMapping("/parents/{parentId}/profile")
    public ResponseEntity<Parent> updateParentProfile(
            @PathVariable String parentId,
            @RequestBody UpdateParentRequest request) {

        Parent parent = parentRepository.findByParentId(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        parent.setName(request.getName());
        parent.setEmail(request.getEmail());
        parent.setPhoneNumber(request.getPhoneNumber());

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            parent.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Parent updated = parentRepository.save(parent);
        return ResponseEntity.ok(updated);
    }
}
