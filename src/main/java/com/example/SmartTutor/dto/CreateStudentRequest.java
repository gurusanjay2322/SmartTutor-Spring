package com.example.SmartTutor.dto;

import lombok.Data;

@Data
public class CreateStudentRequest {
    private String username;
    private String email;
    private String password;
    private String name;
    private String classLevel;
    private String parentPhoneNumber;
}

