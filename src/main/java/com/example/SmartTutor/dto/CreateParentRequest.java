    package com.example.SmartTutor.dto;

    import lombok.Data;

    @Data
    public class CreateParentRequest {
        private String username;
        private String email;
        private String password;
        private String name;
        private String phoneNumber;
        private String studentId;   // optional
    }
