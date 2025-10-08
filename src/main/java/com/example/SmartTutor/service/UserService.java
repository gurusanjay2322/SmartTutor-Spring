package com.example.SmartTutor.service;

import com.example.SmartTutor.model.User;
import com.example.SmartTutor.model.users.Student;
import com.example.SmartTutor.model.users.Parent;
import com.example.SmartTutor.model.users.Admin;
import com.example.SmartTutor.repository.StudentRepository;
import com.example.SmartTutor.repository.ParentRepository;
import com.example.SmartTutor.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final AdminRepository adminRepository;

    // Find user by email across all types
    public User findByEmail(String email) {
        Optional<User> user = studentRepository.findByEmail(email).map(s -> (User) s);
        if (user.isPresent()) return user.get();

        user = parentRepository.findByEmail(email).map(p -> (User) p);
        if (user.isPresent()) return user.get();

        user = adminRepository.findByEmail(email).map(a -> (User) a);
        if (user.isPresent()) return user.get();

        throw new RuntimeException("User not found with email: " + email);
    }

    public User findById(String id) {
        return studentRepository.findById(id).map(s -> (User) s)
                .or(() -> parentRepository.findById(id).map(p -> (User) p))
                .or(() -> adminRepository.findById(id).map(a -> (User) a))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User findByUsername(String username) {
        Optional<User> user = studentRepository.findByUsername(username).map(s -> (User) s);
        if (user.isPresent()) return user.get();

        user = parentRepository.findByUsername(username).map(p -> (User) p);
        if (user.isPresent()) return user.get();

        user = adminRepository.findByUsername(username).map(a -> (User) a);
        if (user.isPresent()) return user.get();

        throw new RuntimeException("User not found with username: " + username);
    }

    // Save user to the correct repository
    public void saveUser(User user) {
        if (user instanceof Student) studentRepository.save((Student) user);
        else if (user instanceof Parent) parentRepository.save((Parent) user);
        else if (user instanceof Admin) adminRepository.save((Admin) user);
        else throw new RuntimeException("Unknown user type");
    }
}
