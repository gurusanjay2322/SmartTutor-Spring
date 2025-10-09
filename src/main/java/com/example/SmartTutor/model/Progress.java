package com.example.SmartTutor.model;


import com.example.SmartTutor.model.users.Student;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "progress")
public class Progress {

    @Id
    private String id;

    @DBRef
    private Student student;

    @DBRef
    private Subject subject;

    private int completedLessons;
    private int totalLessons;
    private double percentage;

    private ProgressStatus status;
    private LocalDateTime lastUpdated;
}

