package it.cigbase.sigarette.service;

import it.cigbase.sigarette.dto.CigaretteResponseDTO;
import it.cigbase.sigarette.entity.Cigarette;
import it.cigbase.sigarette.event.ReviewEvent;
import it.cigbase.sigarette.mapper.CigaretteMapper;
import it.cigbase.sigarette.repository.CigaretteRepository;
import it.cigbase.sigarette.util.ReviewEventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
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

    @JmsListener(destination = "reviews-queue")
    public void recive(ReviewEvent event){
        Long idCigarette = event.getIdCigarette();
        Float rating = event.getRating();
        Float oldRating = event.getOldRating();
        switch (event.getReviewEventType()){
            case ReviewEventType.REVIEW_EVENT_CREATED:
                addRating(idCigarette, rating);
                break;

            case ReviewEventType.REVIEW_EVENT_UPDATED:
                updateRating(idCigarette, rating, oldRating);
                break;

            case ReviewEventType.REVIEW_EVENT_DELETED:
                deleteRating(idCigarette, rating);
                break;

            default:
                throw new IllegalArgumentException("Cosa e successo");
        }
    }

    private void addRating(Long id, Float rating){
        Cigarette cigarette = cigaretteRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Cigarette with id: " + id + " not found")
        );

        int count = cigarette.getRatingsCount();
        Float newAvg = (cigarette.getAvgRating() * count + rating) / (count + 1);

        cigarette.setAvgRating(newAvg);
        cigarette.setRatingsCount(count + 1);
        cigaretteRepository.save(cigarette);
    }

    private void updateRating(Long idCigarette, Float rating, Float oldRating){
        Cigarette cigarette = cigaretteRepository.findById(idCigarette).orElseThrow(
                () -> new IllegalArgumentException("Cigarette with id: " + idCigarette + " not found")
        );

        int count = cigarette.getRatingsCount();
        Float newAvg = (cigarette.getAvgRating() * count - oldRating + rating) / (count);

        cigarette.setAvgRating(newAvg);
        cigaretteRepository.save(cigarette);
    }

    private void deleteRating(Long idCigarette, Float rating){
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

        cigaretteRepository.save(cigarette);
    }

}
