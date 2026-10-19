package it.cigbase.sigarette.service;


import it.cigbase.sigarette.dto.CigaretteResponseDTO;
import it.cigbase.sigarette.entity.Brand;
import it.cigbase.sigarette.entity.Cigarette;
import it.cigbase.sigarette.event.ReviewEvent;
import it.cigbase.sigarette.repository.CigaretteRepository;
import it.cigbase.sigarette.util.ReviewEventType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CigaretteServiceTest {

    @Mock
    private CigaretteRepository cigaretteRepository;

    @InjectMocks
    private CigaretteService cigaretteService;

    private Cigarette cigarette;

    @BeforeEach
    void setUp(){
        Brand brand = Brand.builder()
                .name("camel")
                .build();

        cigarette = Cigarette.builder()
                .id(1L)
                .brand(brand)
                .name("Camel blue")
                .price(5.60F)
                .isMenthol(false)
                .avgRating(0F)
                .ratingsCount(0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void CigaretteService_getCigaretteById_returnCigarette(){
        when(cigaretteRepository.findById(cigarette.getId())).thenReturn(Optional.of(cigarette));

        CigaretteResponseDTO cigaretteResponseDTO = cigaretteService.getCigaretteById(cigarette.getId());

        Assertions.assertThat(cigaretteResponseDTO).isNotNull();
        Assertions.assertThat(cigaretteResponseDTO.id()).isEqualTo(cigarette.getId());
    }

    @Test
    void CigaretteService_Recive_addReviewFirst() {
        when(cigaretteRepository.findById(cigarette.getId())).thenReturn(Optional.of(cigarette));

        cigaretteService.recive(new ReviewEvent(1L, 4f,null, ReviewEventType.REVIEW_EVENT_CREATED));

        ArgumentCaptor<Cigarette> captor = ArgumentCaptor.forClass(Cigarette.class);
        verify(cigaretteRepository).save(captor.capture());
        Assertions.assertThat(captor.getValue().getRatingsCount()).isEqualTo(1);
        Assertions.assertThat(captor.getValue().getAvgRating()).isCloseTo(4f, within(0.001f));
    }

    @Test
    void CigaretteService_Recive_addReviewWithOtherReview() {
        cigarette.setAvgRating(3F);
        cigarette.setRatingsCount(1);
        when(cigaretteRepository.findById(cigarette.getId())).thenReturn(Optional.of(cigarette));

        cigaretteService.recive(new ReviewEvent(1L, 5f, null, ReviewEventType.REVIEW_EVENT_CREATED));

        Assertions.assertThat(cigarette.getRatingsCount()).isEqualTo(2);
        Assertions.assertThat(cigarette.getAvgRating()).isCloseTo(4F, within(0.001f));
        verify(cigaretteRepository).save(cigarette);
    }

    @Test
    void CigaretteService_Recive_throwsError() {
        when(cigaretteRepository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() ->
                cigaretteService.recive(new ReviewEvent(99L, 4f, null, ReviewEventType.REVIEW_EVENT_CREATED)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void CigaretteService_Recive_updateRating(){
        cigarette.setRatingsCount(1);
        cigarette.setAvgRating(4.0F);
        when(cigaretteRepository.findById(cigarette.getId())).thenReturn(Optional.of(cigarette));

        cigaretteService.recive(new ReviewEvent(cigarette.getId(), 3.0F, 4.0F, ReviewEventType.REVIEW_EVENT_UPDATED));

        Assertions.assertThat(cigarette.getAvgRating()).isEqualTo(3.0F);
        Assertions.assertThat(cigarette.getRatingsCount()).isEqualTo(1);
    }

    @Test
    void CigaretteService_Recive_deleteRating(){
        cigarette.setRatingsCount(1);
        cigarette.setAvgRating(4.0f);

        when(cigaretteRepository.findById(cigarette.getId())).thenReturn(Optional.of(cigarette));

        cigaretteService.recive(new ReviewEvent(cigarette.getId(), 4.0F, null, ReviewEventType.REVIEW_EVENT_DELETED));

        Assertions.assertThat(cigarette.getRatingsCount()).isZero();
        Assertions.assertThat(cigarette.getAvgRating()).isEqualTo(0.0F);
    }
}
