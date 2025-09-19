package com.example.SmartTutor.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "subjects")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeSubjects {
    @Id
    private String id;          // e.g., GRADE_1
    private String grade;       // e.g., "GRADE_1"
    private List<Subject> subjects;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Subject {
    private String name;
    private List<Lesson> lessons;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Lesson {
    private String lessonId;
    private String title;
    private List<Material> materials;
    private Quiz quiz;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Material {
    private String type;  // "video", "pdf", "game"
    private String url;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Quiz {
    private String type;  // "MCQ", "TrueFalse", "Game"
    private List<Question> questions;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Question {
    private String q;
    private List<String> options;
    private String answer;
}

