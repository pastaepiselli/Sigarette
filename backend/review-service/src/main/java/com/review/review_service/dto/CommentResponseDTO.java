package com.review.review_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentResponseDTO (
        Long id,
        ReviewResponseDTO dto,
        UUID idUser,
        String description,
        LocalDateTime updatedAt
) {}
