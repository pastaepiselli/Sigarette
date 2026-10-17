package com.review.review_service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewRequestDTO(
        Long idCigarette,
        Float rating,
        String description
) {}
