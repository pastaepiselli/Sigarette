package com.review.review_service.dto;

import lombok.Builder;

@Builder
public record ReviewRequestDTO(
       Long idUser,
       Long idCigarette,
       Float rating,
       String description
) {}
