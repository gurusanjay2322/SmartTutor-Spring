package com.example.SmartTutor.model.users;

import com.example.SmartTutor.model.GradeSubjects;
import com.example.SmartTutor.model.User;
import com.example.SmartTutor.model.Role;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "students")
public class Student extends User {
    @Field("studentId")
    private String studentId;

    private String name;
    private String classLevel;
    private int points;
    private String parentPhoneNumber;
    private String parentId;
    private String schoolId;
    private String schoolName;
    private GradeSubjects gradeSubjects;

    public Student(String username, String email, String password,
                   String name, String classLevel, String parentId,
                   String schoolId, String schoolName) {
        super(username, email, password, Role.STUDENT);
        this.name = name;
        this.classLevel = classLevel;
        this.points = 0;
        this.parentId = parentId;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
    }


    // getters and setters

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

    public String getParentPhoneNumber() { return parentPhoneNumber; }
    public void setParentPhoneNumber(String parentPhoneNumber) { this.parentPhoneNumber = parentPhoneNumber; }

    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }

    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String schoolName) { this.schoolName = schoolName; }

    public GradeSubjects getGradeSubjects() {
        return gradeSubjects;
    }

    public void setGradeSubjects(GradeSubjects gradeSubjects) {
        this.gradeSubjects = gradeSubjects;
    }



}
