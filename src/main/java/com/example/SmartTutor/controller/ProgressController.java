package com.example.SmartTutor.controller;

import com.example.SmartTutor.model.Progress;
import com.example.SmartTutor.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping
    public Progress saveProgress(@RequestBody Progress progress) {
        return progressService.saveProgress(progress);
    }

    @GetMapping("/{studentId}")
    public List<Progress> getStudentProgress(@PathVariable String studentId) {
        return progressService.getProgressByStudent(studentId);
    }

    @PutMapping("/{id}")
    public Progress updateProgress(@PathVariable String id, @RequestBody Progress updatedProgress) {
        return progressService.updateProgress(id, updatedProgress);
    }
}
