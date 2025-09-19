package com.example.SmartTutor.controller;

import com.example.SmartTutor.model.GradeSubjects;
import com.example.SmartTutor.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping("/{grade}")
    public ResponseEntity<GradeSubjects> getSubjectsByGrade(@PathVariable String grade) {
        return ResponseEntity.ok(subjectService.getSubjectsByGrade(grade));
    }
}

