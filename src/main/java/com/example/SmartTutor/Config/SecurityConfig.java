package com.example.SmartTutor.Config;

import com.example.SmartTutor.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Swagger endpoints
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // Auth endpoints (login and forgot-password public)
                        .requestMatchers("/api/auth/login", "/api/auth/forgot-password", "api/auth/reset-password").permitAll()

                        // Super Admin endpoints
                        .requestMatchers("/api/superadmin/**").hasAuthority("SUPER_ADMIN")

                        // School Admin endpoints
                        .requestMatchers("/api/students/create-students/**", "/api/parents/create-parents/**")
                        .hasAuthority("SCHOOL_ADMIN")

                        // everything else requires authentication
                        .anyRequest().authenticated()
                )
                // JWT filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

