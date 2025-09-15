package com.example.SmartTutor.controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.SmartTutor.dto.CreateParentRequest;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ParentController {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create-parents")
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

        if (request.getStudentId() != null) {
            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            student.setParentId(savedParent.getParentId());
            studentRepository.save(student);
        }

        return ResponseEntity.ok(savedParent);
    }
}
