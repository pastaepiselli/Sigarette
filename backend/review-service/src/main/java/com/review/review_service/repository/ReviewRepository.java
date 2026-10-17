package com.review.review_service.repository;

import com.review.review_service.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByIdCigarette(Long idCigarette);

    @Query(
            value = """
    select *
        from review r
    where r.id_user =:idUser
    and r.id =:idReview
    """,nativeQuery = true)
    Optional<Review> findMyReviewWithId(Long idReview, UUID idUser);

}
