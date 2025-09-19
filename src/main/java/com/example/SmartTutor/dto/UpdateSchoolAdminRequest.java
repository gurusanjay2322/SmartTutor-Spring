package com.example.SmartTutor.dto;

import lombok.Data;

// For updating SchoolAdmin profile
@Data
public class UpdateSchoolAdminRequest {
    private String name;
    private String email;
    private String schoolName;
    private String password; // optional
}