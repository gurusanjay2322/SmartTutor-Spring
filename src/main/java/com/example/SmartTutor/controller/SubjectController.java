package com.example.SmartTutor.controller;

import com.example.SmartTutor.model.GradeSubjects;
import com.example.SmartTutor.model.Lesson;
import com.example.SmartTutor.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping("/{grade}")
    public ResponseEntity<GradeSubjects> getSubjectsByGrade(@PathVariable String grade) {
        return ResponseEntity.ok(subjectService.getSubjectsByGrade(grade));
    }

    @PostMapping("/{grade}/{subjectName}/lessons")
    public ResponseEntity<GradeSubjects> addLesson(
            @PathVariable String grade,
            @PathVariable String subjectName,
            @RequestParam(required = false) String subtopic, // optional
            @RequestBody Lesson lesson
    ) {
        return ResponseEntity.ok(subjectService.addLesson(grade, subjectName, subtopic, lesson));
    }

    @PutMapping("/{grade}/{subjectName}/lessons/{lessonId}")
    public ResponseEntity<GradeSubjects> updateLesson(
            @PathVariable String grade,
            @PathVariable String subjectName,
            @RequestParam(required = false) String subtopic,
            @PathVariable String lessonId,
            @RequestBody Lesson updatedLesson
    ) {
        updatedLesson.setLessonId(lessonId);
        return ResponseEntity.ok(subjectService.updateLesson(grade, subjectName, subtopic, updatedLesson));
    }
}
