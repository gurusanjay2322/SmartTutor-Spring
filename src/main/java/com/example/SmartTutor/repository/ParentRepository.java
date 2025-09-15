package com.example.SmartTutor.repository;

import com.example.SmartTutor.model.users.Parent;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ParentRepository extends MongoRepository<Parent, String> {
    Optional<Parent> findByUsername(String username);
}
