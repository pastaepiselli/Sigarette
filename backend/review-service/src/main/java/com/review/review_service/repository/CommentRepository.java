package com.review.review_service.repository;

import com.review.review_service.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndReviewId(Long idReview, Long id);
}

