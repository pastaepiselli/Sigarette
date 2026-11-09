package com.review.review_service.dto;

import java.time.LocalDateTime;

public record ErrorDTO(
        String message,
        int status,
        LocalDateTime dateTime
) {}
