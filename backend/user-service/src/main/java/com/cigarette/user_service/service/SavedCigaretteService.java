package com.cigarette.user_service.service;

import com.cigarette.user_service.dto.SavedCigaretteRequestDTO;
import com.cigarette.user_service.dto.SavedCigaretteResponseDTO;
import com.cigarette.user_service.entity.SavedCigarette;
import com.cigarette.user_service.mapper.SavedCigaretteMapper;
import com.cigarette.user_service.repository.SavedCigaretteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SavedCigaretteService {

    @Autowired
    private SavedCigaretteRepository savedCigaretteRepository;

    public SavedCigaretteResponseDTO saveCigarette(SavedCigaretteRequestDTO savedCigaretteRequestDTO, Jwt jwt){
        UUID userId = UUID.fromString(jwt.getSubject());
        SavedCigarette savedCigarette = SavedCigaretteMapper.mapToEntity(savedCigaretteRequestDTO);
        savedCigarette.setIdUser(userId);
        return SavedCigaretteMapper.mapToDTO(savedCigaretteRepository.save(savedCigarette));
    }

    public List<SavedCigaretteResponseDTO> findAllSavedCigaretteOfUser(UUID idUser){
        return savedCigaretteRepository.findAllSavedCigaretteOfUser(idUser).stream()
                .map(SavedCigaretteMapper::mapToDTO)
                .toList();
    }

    public void deleteSavedCigarette(Long idSavedCigarette){
        SavedCigarette savedCigarette = savedCigaretteRepository.findById(idSavedCigarette).orElseThrow(
                () -> new IllegalArgumentException("Saved cig with id: " + idSavedCigarette + " not found")
        );
        savedCigaretteRepository.delete(savedCigarette);
    }
}
