package com.example.SmartTutor.service;

import com.example.SmartTutor.model.GradeSubjects;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final SubjectService subjectService;

    public Student createStudent(Student student) {
        // fetch GradeSubjects for this classLevel
        GradeSubjects gradeSubjects = subjectService
                .getSubjectsByGrade(student.getClassLevel());

        // attach it to student
        student.setGradeSubjects(gradeSubjects);

        return studentRepository.save(student);
    }
}


