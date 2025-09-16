package com.example.SmartTutor.repository;

import com.example.SmartTutor.model.users.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByUsername(String username);
    Optional<Student> findByStudentId(String studentId);
    List<Student> findByParentId(String parentId);

}
