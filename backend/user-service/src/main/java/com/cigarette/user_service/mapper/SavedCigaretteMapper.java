package com.cigarette.user_service.mapper;

import com.cigarette.user_service.dto.SavedCigaretteRequestDTO;
import com.cigarette.user_service.dto.SavedCigaretteResponseDTO;
import com.cigarette.user_service.entity.SavedCigarette;

import java.time.LocalDateTime;

public class SavedCigaretteMapper {

    public static SavedCigaretteResponseDTO mapToDTO(SavedCigarette savedCigarette){
        return new SavedCigaretteResponseDTO(
                savedCigarette.getId(),
                savedCigarette.getIdCigarette(),
                savedCigarette.getStatus(),
                savedCigarette.getCreatedAt());
    }

    public static SavedCigarette mapToEntity(SavedCigaretteRequestDTO dto){
        return new SavedCigarette(
                null,
                null,
                dto.idCigarette(),
                dto.status(),
                LocalDateTime.now()
        );
    }
}
