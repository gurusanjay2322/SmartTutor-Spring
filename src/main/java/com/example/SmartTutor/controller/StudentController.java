package com.example.SmartTutor.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.repository.StudentRepository;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.dto.CreateStudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")  // base path for all endpoints
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody CreateStudentRequest request) {
        // Ensure parent exists if parentId is provided
        if (request.getParentId() != null) {
            parentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent not found"));
        }

        Student student = new Student(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getClassLevel(),
                request.getParentId()
        );

        student.setStudentId(UUID.randomUUID().toString());
        student.setPoints(0); // default points

        Student savedStudent = studentRepository.save(student);

        return ResponseEntity.ok(savedStudent);
    }
}
