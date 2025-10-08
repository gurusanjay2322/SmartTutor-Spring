package com.example.SmartTutor.model;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subtopic {
    private String name;
    private List<Lesson> lessons;
}
