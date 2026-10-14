package it.cigbase.sigarette.service;

import it.cigbase.sigarette.dto.CigaretteResponseDTO;
import it.cigbase.sigarette.mapper.CigaretteMapper;
import it.cigbase.sigarette.repository.CigaretteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CigaretteService {

    @Autowired
    private CigaretteRepository cigaretteRepository;

    public CigaretteResponseDTO getCigaretteById(Long id){
        return cigaretteRepository.findById(id)
                .map(CigaretteMapper::mapToDTO)
                .orElseThrow(
                        () -> new IllegalArgumentException("Cigarette with id: " + id + " not found")
                );
    }

    public List<CigaretteResponseDTO> getAllCigarette(){
        return cigaretteRepository.findAll().stream()
                .map(CigaretteMapper::mapToDTO)
                .toList();
    }
}
