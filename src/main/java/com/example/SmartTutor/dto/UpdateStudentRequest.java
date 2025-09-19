package com.example.SmartTutor.dto;

import lombok.Data;

@Data
public class UpdateStudentRequest {
    private String name;
    private String email;
    private String classLevel;
    private String parentPhoneNumber;
    private String password; // optional
}