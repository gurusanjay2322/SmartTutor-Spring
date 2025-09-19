package com.example.SmartTutor.repository;

import com.example.SmartTutor.model.GradeSubjects;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface GradeSubjectsRepository extends MongoRepository<GradeSubjects, String> {
    Optional<GradeSubjects> findByGrade(String grade);
}
