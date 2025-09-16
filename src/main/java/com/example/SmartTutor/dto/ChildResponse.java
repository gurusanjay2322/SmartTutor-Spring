package com.example.SmartTutor.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Child/student details under a parent")
public class ChildResponse {
    private String studentId;
    private String name;
    private String classLevel;
    private int points;

    public ChildResponse(String studentId, String name, String classLevel, int points) {
        this.studentId = studentId;
        this.name = name;
        this.classLevel = classLevel;
        this.points = points;
    }

    // ✅ Getters & setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getClassLevel() { return classLevel; }
    public void setClassLevel(String classLevel) { this.classLevel = classLevel; }

    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
}
