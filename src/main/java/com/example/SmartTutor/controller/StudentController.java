package com.example.SmartTutor.controller;

import com.example.SmartTutor.dto.CreateStudentRequest;
import com.example.SmartTutor.dto.StudentProfileResponse;
import com.example.SmartTutor.dto.UpdateStudentRequest;
import com.example.SmartTutor.model.GradeSubjects;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.StudentRepository;
import com.example.SmartTutor.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubjectService subjectService;

    // Student self-register with parent details
    @PostMapping("/register")
    public ResponseEntity<Student> registerStudent(@RequestBody CreateStudentRequest request) {
        // Check unique email and username for student
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        if (studentRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        // Check parent by phone number
        Parent parent = parentRepository.findByPhoneNumber(request.getParentPhoneNumber())
                .map(existingParent -> {
                    if (!existingParent.getName().equals(request.getParentName())) {
                        throw new RuntimeException("Parent exists with a different name");
                    }
                    return existingParent;
                })
                .orElseGet(() -> {
                    // Create new parent if not exists
                    Parent newParent = new Parent(
                            request.getParentName(),
                            request.getEmail() + ".parent@gmail.com",
                            passwordEncoder.encode("defaultPassword123"),
                            request.getParentName(),
                            request.getParentPhoneNumber()
                    );
                    newParent.setParentId(UUID.randomUUID().toString());
                    return parentRepository.save(newParent);
                });

        // Create student
        Student student = new Student(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getClassLevel(),
                parent.getParentId(),
                request.getSchoolId(),
                request.getSchoolName()
        );

        student.setParentPhoneNumber(request.getParentPhoneNumber());
        student.setStudentId(UUID.randomUUID().toString());

        GradeSubjects gradeSubjects = subjectService.getSubjectsByGrade(request.getClassLevel());
        student.setGradeSubjects(gradeSubjects);

        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.ok(savedStudent);
    }


    // ✅ Student view own profile
    @GetMapping("/me/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentProfileResponse> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Parent parent = parentRepository.findByParentId(student.getParentId())
                .orElse(null);

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

    // ✅ Student update own profile
    @PutMapping("/me/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Student> updateMyProfile(
            Authentication authentication,
            @RequestBody UpdateStudentRequest request) {

        String username = authentication.getName();
        Student student = studentRepository.findByUsername(username)
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
}
