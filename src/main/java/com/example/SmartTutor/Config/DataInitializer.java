package com.example.SmartTutor.Config;

import com.example.SmartTutor.model.Role;
import com.example.SmartTutor.model.User;
import com.example.SmartTutor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Load values from application.properties or environment variables
    @Value("${admin.username:admin}")
    private String adminUsername;

    @Value("${admin.email:admin@smarttutor.com}")
    private String adminEmail;

    @Value("${admin.password:admin123}") // default only for local dev
    private String adminPassword;

    @Bean
    CommandLineRunner initAdmin() {
        return args -> {
            if (userRepository.findByUsername(adminUsername).isEmpty()) {
                User admin = User.builder()
                        .username(adminUsername)
                        .email(adminEmail)
                        .password(passwordEncoder.encode(adminPassword))
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
                System.out.println("Default Admin user created successfully.");
            } else {
                System.out.println("Admin already exists, skipping creation.");
            }
        };
    }
}
