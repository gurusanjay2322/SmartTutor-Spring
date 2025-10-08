package com.example.SmartTutor.dto;

import lombok.Data;

@Data
public class CreateStudentRequest {
    private String username;
    private String email;
    private String password;
    private String name;
    private String classLevel;

    // parent details
    private String parentName;
    private String parentPhoneNumber;

    // school details
    private String schoolId;
    private String schoolName;
}

