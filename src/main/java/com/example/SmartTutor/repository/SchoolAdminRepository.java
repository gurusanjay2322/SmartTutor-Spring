package com.example.SmartTutor.repository;

import com.example.SmartTutor.model.User;
import com.example.SmartTutor.model.users.SchoolAdmin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SchoolAdminRepository extends MongoRepository<SchoolAdmin, String> {
    boolean existsByUsername(String username);

    Optional<SchoolAdmin> findByUsername(String username);
}

