package com.example.SmartTutor.dto;

import lombok.Data;

@Data
public class UpdateParentRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String password; // ✅ add this

    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

