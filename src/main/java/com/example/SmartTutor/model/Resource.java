package com.example.SmartTutor.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resource {
    private String type;   // "PDF", "Quiz"
    private String title;
    private String url;
}
