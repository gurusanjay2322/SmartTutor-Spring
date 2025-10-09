package com.example.SmartTutor.service;

import com.example.SmartTutor.model.Progress;
import com.example.SmartTutor.repository.ProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final ProgressRepository progressRepository;

    public Progress saveProgress(Progress progress) {
        return progressRepository.save(progress);
    }

    public List<Progress> getProgressByStudent(String studentId) {
        return progressRepository.findByStudentId(studentId);
    }

    public Progress updateProgress(String id, Progress updatedProgress) {
        Progress existing = progressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progress not found"));
        existing.setCompletedLessons(updatedProgress.getCompletedLessons());
        existing.setTotalLessons(updatedProgress.getTotalLessons());
        existing.setPercentage(updatedProgress.getPercentage());
        existing.setStatus(updatedProgress.getStatus());
        return progressRepository.save(existing);
    }
}
