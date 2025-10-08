package com.example.SmartTutor.repository;

import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.model.users.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ParentRepository extends MongoRepository<Parent, String> {
    Optional<Parent> findByPhoneNumber(String phoneNumber);

    Optional<Parent> findByUsername(String username);
    Optional<Parent> findByParentId(String parentId);
    Optional<Student> findByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

}
