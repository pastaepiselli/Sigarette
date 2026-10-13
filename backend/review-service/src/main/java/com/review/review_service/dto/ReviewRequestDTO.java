package com.review.review_service.dto;

public record ReviewRequestDTO(
       Long idUser,
       Long idCigarette,
       Float rating,
       String description
) {}
