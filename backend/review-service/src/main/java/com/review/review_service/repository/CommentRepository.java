package com.review.review_service.repository;

import com.review.review_service.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value =
    """
    select * 
        from comment c 
    where c.id =:id
    and id_review =:idReview
    """, nativeQuery = true)
    Optional<Comment> findByIdAndIdReview(Long idReview, Long id);

    @Query(value =
    """
    select *
        from comment c
    where c.id_review =:idReview
    """, nativeQuery = true)
    List<Comment> findAllCommentsByIdReview(Long idReview);

}

