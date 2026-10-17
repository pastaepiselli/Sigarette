package com.review.review_service.repository;

import com.review.review_service.entity.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // database in memoria H2
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private Review savedReview;
    @BeforeEach
    void setUp(){
        // create a review and save it
        UUID userId = UUID.randomUUID();
        Review review = Review.builder()
                .idUser(userId)
                .idCigarette(1L)
                .rating(4.5F)
                .description("Buona sigaretta")
                .updatedAt(LocalDateTime.now())
                .build();

        savedReview = reviewRepository.save(review);
    }

    @Test
    void ReviewRepository_findMyReviewWithId_returnReview(){
        Review foundReview = reviewRepository.findMyReviewWithId(savedReview.getId(), savedReview.getIdUser()).get();

        Assertions.assertThat(foundReview).isNotNull();
        Assertions.assertThat(foundReview.getIdUser()).isEqualTo(savedReview.getIdUser());
        Assertions.assertThat(foundReview.getId()).isEqualTo(savedReview.getId());
    }

}
