package com.review.review_service.dto;

import java.time.LocalDateTime;

public record ReviewResponseDTO (
    Long id,
    Long idUser,
    Long idCigarette,
    Float rating,
    String description,
    LocalDateTime dtInserimento
){}
