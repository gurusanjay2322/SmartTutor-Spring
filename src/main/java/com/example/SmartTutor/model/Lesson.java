package com.example.SmartTutor.model;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lesson {
    private String lessonId;
    private String title;
    private String description;
    private String videoUrl;
    private String content;
    private List<Resource> resources;
    private int order;
}
