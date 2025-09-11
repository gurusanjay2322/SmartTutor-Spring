package com.example.SmartTutor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SchoolAdminResponse {
    private String message;
    private String id; // optional, can be null if not applicable
}
