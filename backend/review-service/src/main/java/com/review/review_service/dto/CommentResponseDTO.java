package com.review.review_service.dto;

import java.time.LocalDateTime;

public record CommentResponseDTO (
        Long id,
        ReviewResponseDTO dto,
        String description,
        LocalDateTime dtInserimento
) {}
