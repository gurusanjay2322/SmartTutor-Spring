package com.example.SmartTutor.service;

import com.example.SmartTutor.model.*;
import com.example.SmartTutor.repository.GradeSubjectsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final GradeSubjectsRepository subjectRepository;

    public GradeSubjects getSubjectsByGrade(String grade) {
        return subjectRepository.findByGrade(grade)
                .orElseThrow(() -> new RuntimeException("No subjects found for grade: " + grade));
    }

    public GradeSubjects addLesson(String grade, String subjectName, String subtopicName, Lesson lesson) {
        GradeSubjects gradeSubjects = getSubjectsByGrade(grade);

        Optional<Subject> subjectOpt = gradeSubjects.getSubjects().stream()
                .filter(s -> s.getName().equalsIgnoreCase(subjectName))
                .findFirst();

        if (subjectOpt.isEmpty())
            throw new RuntimeException("Subject not found: " + subjectName);

        Subject subject = subjectOpt.get();

        if (subtopicName != null && !subtopicName.isEmpty()) {
            Optional<Subtopic> subtopicOpt = subject.getSubtopics().stream()
                    .filter(st -> st.getName().equalsIgnoreCase(subtopicName))
                    .findFirst();

            if (subtopicOpt.isEmpty())
                throw new RuntimeException("Subtopic not found: " + subtopicName);

            subtopicOpt.get().getLessons().add(lesson);
        } else {
            subject.getLessons().add(lesson);
        }

        return subjectRepository.save(gradeSubjects);
    }

    public GradeSubjects updateLesson(String grade, String subjectName, String subtopicName, Lesson updatedLesson) {
        GradeSubjects gradeSubjects = getSubjectsByGrade(grade);

        Subject subject = gradeSubjects.getSubjects().stream()
                .filter(s -> s.getName().equalsIgnoreCase(subjectName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Subject not found: " + subjectName));

        List<Lesson> lessons;

        if (subtopicName != null && !subtopicName.isEmpty()) {
            Subtopic subtopic = subject.getSubtopics().stream()
                    .filter(st -> st.getName().equalsIgnoreCase(subtopicName))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Subtopic not found: " + subtopicName));
            lessons = subtopic.getLessons();
        } else {
            lessons = subject.getLessons();
        }

        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonId().equals(updatedLesson.getLessonId())) {
                lessons.set(i, updatedLesson);
                return subjectRepository.save(gradeSubjects);
            }
        }

        throw new RuntimeException("Lesson not found: " + updatedLesson.getLessonId());
    }
}
