package com.cigarette.user_service.dto;


import com.cigarette.user_service.util.Status;

import java.time.LocalDateTime;

public record SavedCigaretteResponseDTO(
        Long id,
        Long idCigarette,
        Status status,
        LocalDateTime createdAt
) {}
