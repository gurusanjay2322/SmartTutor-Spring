package com.example.SmartTutor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentProfileResponse {
    private String studentId;
    private String name;
    private String email;
    private String username;
    private int points;
    private String parentName;
    private String parentPhoneNumber;
}
