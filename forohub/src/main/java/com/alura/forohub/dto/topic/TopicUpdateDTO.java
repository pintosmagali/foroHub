package com.alura.forohub.dto.topic;

public record TopicUpdateDTO(
        String title,
        String message,
        String status,   // "OPEN" | "CLOSED" | etc.
        Long courseId
) {}

