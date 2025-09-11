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
    @Value("${superadmin.username:superadmin}")
    private String superAdminUsername;

    @Value("${superadmin.email:superadmin@smarttutor.com}")
    private String superAdminEmail;

    @Value("${superadmin.password:super123}") // default only for local dev
    private String superAdminPassword;

    @Bean
    CommandLineRunner initSuperAdmin() {
        return args -> {
            if (userRepository.findByUsername(superAdminUsername).isEmpty()) {
                User superAdmin = User.builder()
                        .username(superAdminUsername)
                        .email(superAdminEmail)
                        .password(passwordEncoder.encode(superAdminPassword))
                        .role(Role.SUPER_ADMIN)
                        .build();
                userRepository.save(superAdmin);
                System.out.println("Super Admin user created successfully.");
            } else {
                System.out.println("Super Admin already exists, skipping creation.");
            }
        };
    }
}
