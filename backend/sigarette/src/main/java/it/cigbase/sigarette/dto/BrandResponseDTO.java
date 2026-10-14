package it.cigbase.sigarette.dto;

public record BrandResponseDTO(
        Long id,
        String name,
        String country,
        String description,
        String logo_url
) {}
