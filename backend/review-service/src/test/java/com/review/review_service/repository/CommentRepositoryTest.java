package com.review.review_service.repository;

import com.review.review_service.entity.Comment;
import com.review.review_service.entity.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest // per indicare a spring i test con jpa
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // invece di usare il database reale ne usiamo un h2, andiamo a creare un finta persitenza insomma
public class CommentRepositoryTest {

    // ovviamente istanzio la classe da testare
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void CommentRepository_findAllCommentsByIdReview(){
        // gia lo sai
        Review review = Review.builder()
                .idUser(1L)
                .idCigarette(1L)
                .rating(1.5F)
                .description("Ew immagina fumarti sta sigaretta")
                .dtInserimento(LocalDateTime.now())
                .build();

        Comment comment1 = Comment.builder()
                .review(review)
                .idUser(3L)
                .description("Ma che dici e la mia sigaretta preferita zitto")
                .dtInserimento(LocalDateTime.now())
                .build();

        Comment comment2 = Comment.builder()
                .review(review)
                .idUser(4L)
                .description("Hai ragione e una merda")
                .dtInserimento(LocalDateTime.now())
                .build();

        Review savedReview = reviewRepository.save(review);
        Comment savedComment1 = commentRepository.save(comment1);
        Comment savedComment2 = commentRepository.save(comment2);

        // act
        List<Comment> list = commentRepository.findAllCommentsByIdReview(savedReview.getId());

        Assertions.assertThat(list).isNotNull();
        Assertions.assertThat(list.size()).isEqualTo(2);
    }

    // per il nome e consigliabile avere una struttura fissa
    @Test // indica un test
    public void CommentRepository_findByIdAndidReview_returnComment(){
        // arrange, creo una review e un commento sotto di essa
        Review review = Review.builder()
                .idUser(2L)
                .idCigarette(1L)
                .rating(5.0F)
                .description("La mia sigaretta preferita!!!!")
                .dtInserimento(LocalDateTime.now())
                .build();

        Comment comment = Comment.builder()
                .review(review)
                .idUser(4L)
                .description("Pure la mia")
                .dtInserimento(LocalDateTime.now())
                .build();

        Review savedReview = reviewRepository.save(review);
        Comment savedComment = commentRepository.save(comment);
        // act
        Comment retrivedComment = commentRepository.findByIdAndIdReview(savedReview.getId(),savedComment.getId()).get();

        // assert
        Assertions.assertThat(retrivedComment).isNotNull();
    }

}
