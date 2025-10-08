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
    private String id;
    private String grade;
    private List<Subject> subjects;
}
