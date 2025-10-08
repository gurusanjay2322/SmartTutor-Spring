package com.example.SmartTutor.model.users;

import com.example.SmartTutor.model.Role;
import com.example.SmartTutor.model.User;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
public class Admin extends User {

    private String adminId;
    private String name;

    public Admin(String username, String email, String password, String name) {
        super(username, email, password, Role.ADMIN);
        this.name = name;
    }

    // Getters & setters
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
