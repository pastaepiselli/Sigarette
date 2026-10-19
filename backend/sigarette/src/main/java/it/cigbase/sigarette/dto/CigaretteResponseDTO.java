package it.cigbase.sigarette.dto;

import java.time.LocalDateTime;

public record CigaretteResponseDTO (
       Long id,
       BrandResponseDTO brand,
       String name,
       Float price,
       String description,
       String imageUrl,
       Float nicotineMG,
       Float tar,
       Float co,
       String format,
       Integer sticksPerPack,
       Boolean isMenthol,
       Float avgRating,
       Integer ratingCount,
       LocalDateTime createdAt
){}
