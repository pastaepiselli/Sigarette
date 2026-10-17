package com.review.review_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponseDTO (
    Long id,
    UUID idUser,
    Long idCigarette,
    Float rating,
    String description,
    LocalDateTime dtInserimento
){}
