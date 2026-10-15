package com.review.review_service.service;

import com.review.review_service.clients.CigaretteClient;
import com.review.review_service.dto.ReviewRequestDTO;
import com.review.review_service.dto.ReviewResponseDTO;
import com.review.review_service.entity.Review;
import com.review.review_service.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    // ovviamente mockiamo (imitiamo) il comportamento del repository per non scrivere ogni volta sul db

    /*
    Mock imita la classe, injectMOcks crea un istanza della classe sotto l'annotazione e inserisce nel costruttore di essa i mock definiti
    in precednza
     */
    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CigaretteClient cigaretteClient;

    @InjectMocks
    private ReviewService reviewService;


    @Test
    public void ReviewService_CreateReview_returnReviewResponseDTO() {
        Review review = Review.builder()
                .idUser(1L)
                .idCigarette(2L)
                .rating(4.0F)
                .description("Una buona sigaretta")
                .dtInserimento(LocalDateTime.now())
                .build();

        ReviewRequestDTO requestDTO = ReviewRequestDTO.builder()
                .idUser(1L)
                .idCigarette(2L)
                .rating(4.0F)
                .description("Una buona sigaretta")
                .build();

        // quando questo metodo vieene chiamato con argomento un qualsiasi Reivew ritorna la review che ho specificato
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        // se vieme chiamato questo metodo di quest classe non tornoare niente non lo eseguire
        // perche apputn andrebbe in errore perhce dovrebbe trovare l'altro servizio aperto
        // per quello ci pensero dopo
        doNothing().when(cigaretteClient).updateAvgRating(anyLong(), anyFloat());

        ReviewResponseDTO responseDTO = reviewService.createReview(requestDTO);

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.idUser()).isEqualTo(requestDTO.idUser());
    }

    @Test
    public void ReviewService_getAllReviewFromCig() {
        Long idCigarette = 2L;

        // creo la risposta che mi da il repository
        Review review = Review.builder()
                .id(1L)
                .idUser(1L)
                .idCigarette(2L)
                .rating(4.0F)
                .description("Una buona sigaretta")
                .dtInserimento(LocalDateTime.now())
                .build();

        List<Review> list = List.of(review);

        // vado a dirgli di ritornami quest aripsota
        when(reviewRepository.findAllByIdCigarette(idCigarette)).thenReturn(list);

        // act
        List<ReviewResponseDTO> returned = reviewService.getAllReviewsFromCig(idCigarette);

        // assert
        Assertions.assertThat(returned).isNotNull();
        Assertions.assertThat(returned).hasSize(1);
    }

    @Test
    public void ReviewService_getReviewById_returnReviewResponseDTO() {

        Review review = Review.builder()
                .id(1L)
                .idUser(1L)
                .idCigarette(2L)
                .rating(4.0F)
                .description("Una buona sigaretta")
                .dtInserimento(LocalDateTime.now())
                .build();

        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

        ReviewResponseDTO responseDTO = reviewService.getReviewById(review.getId());

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.id()).isEqualTo(review.getId());
    }

    @Test
    public void ReviewService_updateReview_returnReviewResponseDTO() {
        Review review = Review.builder()
                .id(1L)
                .idUser(1L)
                .idCigarette(2L)
                .rating(4.0F)
                .description("Una buona sigaretta")
                .dtInserimento(LocalDateTime.now())
                .build();

        ReviewRequestDTO requestDTO = ReviewRequestDTO.builder()
                .idUser(1L)
                .idCigarette(1L)
                .rating(3.0F)
                .description("Recensione aggiornata")
                .build();

        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewResponseDTO responseDTO = reviewService.updateReview(review.getId(), requestDTO);

        Assertions.assertThat(responseDTO).isNotNull();
        Assertions.assertThat(responseDTO.rating()).isEqualTo(requestDTO.rating());
        Assertions.assertThat(responseDTO.description()).isEqualTo(requestDTO.description());
    }
}