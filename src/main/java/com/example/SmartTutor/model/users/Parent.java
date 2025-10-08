package com.example.SmartTutor.model.users;

import com.example.SmartTutor.model.User;
import com.example.SmartTutor.model.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "parents")
public class Parent extends User {
    private String parentId;

    @Indexed(unique = true)
    private String phoneNumber;

    @Indexed(unique = true)
    private String name;


    public Parent(String username, String email, String password,
                  String name, String phoneNumber) {
        super(username, email, password, Role.PARENT);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // getters & setters
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
