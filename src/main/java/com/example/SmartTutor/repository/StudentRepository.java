package com.example.SmartTutor.repository;

import com.example.SmartTutor.model.users.Student;
import com.mongodb.client.model.Filters;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByUsername(String username);
    Optional<Student> findByStudentId(String studentId);
    Optional<Student> findByEmail(String email);
    List<Student> findByParentId(String parentId);


}
