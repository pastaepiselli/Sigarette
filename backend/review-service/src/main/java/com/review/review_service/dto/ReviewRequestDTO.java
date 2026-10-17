package com.review.review_service.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewRequestDTO(
       Float rating,
       String description
) {}
