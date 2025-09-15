package com.example.SmartTutor.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.model.users.SchoolAdmin;
import com.example.SmartTutor.repository.StudentRepository;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.SchoolAdminRepository;
import com.example.SmartTutor.dto.CreateStudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private SchoolAdminRepository schoolAdminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create-students")
    public ResponseEntity<Student> createStudent(@RequestBody CreateStudentRequest request,
                                                 Authentication authentication) {
        String adminUsername = authentication.getName();

        // find school admin by username
        SchoolAdmin admin = schoolAdminRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("SchoolAdmin not found"));


        Student student = new Student(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getClassLevel(),
                request.getParentId(),
                admin.getId(),          // use Mongo _id as schoolId
                admin.getSchoolName()   // denormalized schoolName
        );

        student.setStudentId(UUID.randomUUID().toString());

        Student savedStudent = studentRepository.save(student);

        return ResponseEntity.ok(savedStudent);
    }
}
