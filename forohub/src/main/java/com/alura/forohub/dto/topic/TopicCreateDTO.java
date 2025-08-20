package com.alura.forohub.dto.topic;

public record TopicCreateDTO(
        String title,
        String message,
        Long courseId
) {}
