package com.cigarette.user_service.service;

import com.cigarette.user_service.dto.SavedCigaretteRequestDTO;
import com.cigarette.user_service.dto.SavedCigaretteResponseDTO;
import com.cigarette.user_service.entity.SavedCigarette;
import com.cigarette.user_service.mapper.SavedCigaretteMapper;
import com.cigarette.user_service.repository.SavedCigaretteRepository;
import com.cigarette.user_service.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SavedCigaretteService {

    @Autowired
    private SavedCigaretteRepository savedCigaretteRepository;

    /**
     * Salva una sigaretta in una delle due liste, preferite o vorrei fumare a seconda del parametro
     * nel DTO.
     * @param savedCigaretteRequestDTO
     * @param jwt
     * @return dto della sigaretta salvata
     */
    public SavedCigaretteResponseDTO saveCigarette(SavedCigaretteRequestDTO savedCigaretteRequestDTO, Jwt jwt){
        UUID userId = UUID.fromString(jwt.getSubject());
        SavedCigarette savedCigarette = SavedCigaretteMapper.mapToEntity(savedCigaretteRequestDTO);
        savedCigarette.setIdUser(userId);
        return SavedCigaretteMapper.mapToDTO(savedCigaretteRepository.save(savedCigarette));
    }

    /**
     * Trova lista di sigarette preferite o che vuole provare, a seconda del parametro nell'url dell'utente autenticato
     * @param jwt
     * @param status
     * @return lista di sigarette salvate
     */
    public List<SavedCigaretteResponseDTO> findAllSavedCigaretteOfUser(Jwt jwt, Status status){
        UUID idUser = UUID.fromString(jwt.getSubject());
        return savedCigaretteRepository.findAllSavedCigaretteOfUser(idUser, status).stream()
                .map(SavedCigaretteMapper::mapToDTO)
                .toList();
    }

    /**
     * Trova la lista di sigarette o preferite o che vuole provare un altro utente, la ricerca avviene per username
     * @param usename
     * @param status
     * @return lista di sigarette salvate di un altro utente
     */
    public List<SavedCigaretteResponseDTO> findAllSavedCigaretteOfUserByUsername(String usename, Status status){
        return savedCigaretteRepository.findAllByUsername(usename, status.toString()).stream()
                .map(SavedCigaretteMapper::mapToDTO)
                .toList();
    }

    /**
     * Elimina una sigaretta salvata, presa per id da una delle 2 liste dell'utente autenticato
     * @param idSavedCigarette
     * @param jwt
     */
    public void deleteSavedCigarette(Long idSavedCigarette, Jwt jwt){
        UUID idUser = UUID.fromString(jwt.getSubject());
        SavedCigarette savedCigarette = savedCigaretteRepository.findByIdAndIdUser(idSavedCigarette, idUser).orElseThrow(
                () -> new IllegalArgumentException("Saved cig with id: " + idSavedCigarette + " not found for this user")
        );
        savedCigaretteRepository.delete(savedCigarette);
    }
}
