package com.cigarette.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO (
        @NotBlank(message = "Username is required")
        String username,
        String imageUrl,
        String bio
){}
