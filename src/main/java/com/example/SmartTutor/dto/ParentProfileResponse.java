package com.example.SmartTutor.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "Parent profile response with all children")
public class ParentProfileResponse {
    private String parentId;
    private String name;
    private String email;
    private String username;
    private String phoneNumber;
    private List<ChildResponse> children;

    public ParentProfileResponse(String parentId, String name, String email,
                                 String username, String phoneNumber,
                                 List<ChildResponse> children) {
        this.parentId = parentId;
        this.name = name;
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.children = children;
    }

    // ✅ Getters & setters are required for Swagger/Jackson
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public List<ChildResponse> getChildren() { return children; }
    public void setChildren(List<ChildResponse> children) { this.children = children; }
}
