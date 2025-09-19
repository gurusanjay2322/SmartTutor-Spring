package com.example.SmartTutor.service;

import com.example.SmartTutor.model.GradeSubjects;
import com.example.SmartTutor.repository.GradeSubjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final GradeSubjectsRepository subjectRepository;

    public GradeSubjects getSubjectsByGrade(String grade) {
        return subjectRepository.findByGrade(grade)
                .orElseThrow(() -> new RuntimeException("No subjects found for grade: " + grade));
    }
}



