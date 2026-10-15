package com.review.review_service.repository;

import com.review.review_service.entity.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;


    @Test
    public void ReviewRepository_save_returnReview(){
        // preparo la roba
        Review review = Review.builder()
                .idUser(1L)
                .idCigarette(2L)
                .rating(4.5F)
                .description("Una sigaretta molto buona che fumo giornalmente")
                .dtInserimento(LocalDateTime.now())
                .build();

        // chiamo il metodo da testare
        Review savedReview = reviewRepository.save(review);

        // assert, controllo che l'output sia quello aspettato

        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_findAllReviewsByCig_returnList(){

        // dopo aver testato che il save funziona contorllo se questa query va
        Long idCigarette = 1L;
        Review review1 = Review.builder()
                .idUser(1L)
                .idCigarette(idCigarette)
                .rating(3.5F)
                .description("Sigaretta che ho comprato per molto tempo, non la apprezzo piu tanto")
                .dtInserimento(LocalDateTime.now())
                .build();

        Review review2 = Review.builder()
                .idUser(2L)
                .idCigarette(idCigarette)
                .rating(2.5F)
                .description("Un mio amico le fuma fanno cagare")
                .dtInserimento(LocalDateTime.now())
                .build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        // act
        List<Review> list = reviewRepository.findAllByIdCigarette(idCigarette);

        // assert
        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

}
