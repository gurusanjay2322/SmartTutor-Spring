package com.example.SmartTutor.model.users;

import com.example.SmartTutor.model.Role;
import com.example.SmartTutor.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "schooladmins")
@Data
@NoArgsConstructor
public class SchoolAdmin extends User {

    private String schoolName;
    private String location;

    public SchoolAdmin(String username, String email, String password, String schoolName, String location) {
        super(username, email, password, Role.SCHOOL_ADMIN);
        this.schoolName = schoolName;
        this.location = location;
    }

    public void setName(String name) {
    }
}
