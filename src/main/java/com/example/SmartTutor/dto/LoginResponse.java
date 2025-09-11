package com.example.SmartTutor.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String role;
    private String token;
    private long expiresIn; // in seconds
}

