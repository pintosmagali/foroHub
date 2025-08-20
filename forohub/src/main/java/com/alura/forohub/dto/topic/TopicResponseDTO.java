package com.alura.forohub.dto.topic;

import java.time.OffsetDateTime;

public record TopicResponseDTO(
        Long id,
        String title,
        String message,
        String authorEmail,   // o authorName si después querés cambiarlo
        Long courseId,
        String courseName,
        OffsetDateTime createdAt
) {}