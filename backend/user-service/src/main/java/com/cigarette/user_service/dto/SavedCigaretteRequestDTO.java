package com.cigarette.user_service.dto;

import com.cigarette.user_service.util.Status;

import java.util.UUID;

public record SavedCigaretteRequestDTO(
        Long idCigarette,
        Status status
) {}
