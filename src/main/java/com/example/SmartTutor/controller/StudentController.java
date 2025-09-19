package com.example.SmartTutor.controller;

import com.example.SmartTutor.model.GradeSubjects;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.service.SubjectService;
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
import com.example.SmartTutor.dto.StudentProfileResponse;

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

    @Autowired
    private SubjectService subjectService;
    @PostMapping("/create-students")
    public ResponseEntity<Student> createStudent(@RequestBody CreateStudentRequest request,
                                                 Authentication authentication) {
        String adminUsername = authentication.getName();

        SchoolAdmin admin = schoolAdminRepository.findByUsername(adminUsername)
                .orElseThrow(() -> new RuntimeException("SchoolAdmin not found"));

        // Look for parent by phone number
        Parent parent = parentRepository.findByPhoneNumber(request.getParentPhoneNumber())
                .orElse(null);

        Student student = new Student(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getClassLevel(),
                parent != null ? parent.getId() : null,   // set parentId if parent exists
                admin.getId(),
                admin.getSchoolName()
        );

        student.setParentPhoneNumber(request.getParentPhoneNumber());
        student.setStudentId(UUID.randomUUID().toString());

        // ✅ Fetch subjects automatically based on grade/classLevel
        GradeSubjects gradeSubjects = subjectService.getSubjectsByGrade(request.getClassLevel());
        student.setGradeSubjects(gradeSubjects);



        Student savedStudent = studentRepository.save(student);

        return ResponseEntity.ok(savedStudent);
    }

    @GetMapping("/me/profile")
    public ResponseEntity<StudentProfileResponse> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Parent parent = null;
        if (student.getParentId() != null) {
            parent = parentRepository.findById(student.getParentId()).orElse(null);
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
}
