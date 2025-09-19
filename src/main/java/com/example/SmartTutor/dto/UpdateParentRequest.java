package com.example.SmartTutor.dto;

import lombok.Data;

@Data
public class UpdateParentRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String password; // optional
}
