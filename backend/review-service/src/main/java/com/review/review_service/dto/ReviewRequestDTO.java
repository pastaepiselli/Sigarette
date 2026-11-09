package com.review.review_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewRequestDTO(
        @Positive
        @NotNull
        Long idCigarette,
        @Positive
        @NotNull
        Float rating,
        @NotBlank
        String description
) {}
