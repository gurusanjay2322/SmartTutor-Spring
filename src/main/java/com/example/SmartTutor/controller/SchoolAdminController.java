package com.example.SmartTutor.controller;

import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.StudentRepository;
import com.example.SmartTutor.dto.StudentProfileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/schooladmin")
@PreAuthorize("hasRole('SCHOOL_ADMIN')")  // restrict everything in this controller to admins
public class SchoolAdminController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository parentRepository;

    /**
     * Get profile of any student by studentId (Admin Only)
     */
    @GetMapping("/students/{studentId}/profile")
    public ResponseEntity<StudentProfileResponse> getStudentProfile(@PathVariable String studentId) {
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // Fetch parent if linked
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
