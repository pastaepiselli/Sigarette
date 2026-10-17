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

    public CigaretteResponseDTO addRating(Long id, Float rating){
        Cigarette cigarette = cigaretteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Cigarette with id: " + id + " not found")
        );

        int count = cigarette.getRatingsCount();
        Float newAvg = (cigarette.getAvgRating() * count + rating) / (count + 1);

        cigarette.setAvgRating(newAvg);
        cigarette.setRatingsCount(count + 1);
        return CigaretteMapper.mapToDTO(cigaretteRepository.save(cigarette));
    }

    public CigaretteResponseDTO deleteRating(Long idCigarette, Float rating){
        Cigarette cigarette = cigaretteRepository.findById(idCigarette).orElseThrow(
                () -> new IllegalArgumentException("Cigarette with id: " + idCigarette + " not found")
        );

        int count = cigarette.getRatingsCount();
        if (count <= 0){
            throw new IllegalArgumentException("There arent reviews to remove");
        }

        Float newAvg = count - 1 == 0 ? 0 : (cigarette.getAvgRating() * count - rating) / (count - 1);

        cigarette.setAvgRating(newAvg);
        cigarette.setRatingsCount(count - 1);

        return CigaretteMapper.mapToDTO(cigaretteRepository.save(cigarette));
    }
}
