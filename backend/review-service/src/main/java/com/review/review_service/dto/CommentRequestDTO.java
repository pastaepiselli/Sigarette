package com.review.review_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentRequestDTO (
        @NotBlank
        String description
){}
