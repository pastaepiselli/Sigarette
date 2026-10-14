package it.cigbase.sigarette.service;

import it.cigbase.sigarette.dto.CigaretteResponseDTO;
import it.cigbase.sigarette.entity.Cigarette;
import it.cigbase.sigarette.mapper.CigaretteMapper;
import it.cigbase.sigarette.repository.CigaretteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

    public CigaretteResponseDTO updateAvgRating(Long id, Float rating){
        Cigarette cigarette = cigaretteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Cigarette with id: " + id + " not found")
        );

        Integer ratingsCount = cigarette.getRatingsCount() + 1;
        Float sum = cigarette.getAvgRating() + rating;

        Float newAvg = sum / ratingsCount;

        cigarette.setAvgRating(newAvg);
        return CigaretteMapper.mapToDTO(cigaretteRepository.save(cigarette));
    }
}
