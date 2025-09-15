package com.example.SmartTutor.model.users;

import com.example.SmartTutor.model.User;
import com.example.SmartTutor.model.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class Student extends User {
    private String studentId; // separate from MongoDB _id

    private String name;
    private String classLevel;
    private int points;
    private String parentId; // reference to parent

    public Student(String username, String email, String password,
                   String name, String classLevel, String parentId) {
        super(username, email, password, Role.STUDENT);
        this.name = name;
        this.classLevel = classLevel;
        this.points = 0; // default
        this.parentId = parentId;
    }

    // getters & setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getClassLevel() { return classLevel; }
    public void setClassLevel(String classLevel) { this.classLevel = classLevel; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }
}
