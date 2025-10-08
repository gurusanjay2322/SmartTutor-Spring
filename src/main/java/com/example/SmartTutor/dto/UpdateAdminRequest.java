package com.example.SmartTutor.dto;

import lombok.Data;

@Data
public class UpdateAdminRequest {
    private String name;
    private String email;
    private String password; // optional
}
