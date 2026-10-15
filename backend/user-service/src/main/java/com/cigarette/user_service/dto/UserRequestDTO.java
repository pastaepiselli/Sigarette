package com.cigarette.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO (
        @NotBlank
        String username,
        String imageUrl,
        String bio
){}
