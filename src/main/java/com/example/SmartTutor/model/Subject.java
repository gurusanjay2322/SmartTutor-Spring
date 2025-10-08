package com.example.SmartTutor.model;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {
    private String name;
    private List<Subtopic> subtopics;
    private List<Lesson> lessons;
}
