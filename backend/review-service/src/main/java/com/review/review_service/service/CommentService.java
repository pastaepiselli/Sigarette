package com.review.review_service.service;

import com.review.review_service.dto.CommentRequestDTO;
import com.review.review_service.dto.CommentResponseDTO;
import com.review.review_service.entity.Comment;
import com.review.review_service.entity.Review;
import com.review.review_service.mapper.CommentMapper;
import com.review.review_service.repository.CommentRepository;
import com.review.review_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public CommentResponseDTO getCommentById(Long idReview, Long id){
        return commentRepository.findByIdAndReview(idReview, id)
                .map(CommentMapper::mapToDTO)
                .orElseThrow(
                        () -> new IllegalArgumentException("Comment with id: " + id + " not found")
                );
    }

    public CommentResponseDTO createComment(Long idReview, CommentRequestDTO dto){
        Comment comment = CommentMapper.mapToEntity(dto);
        Review review = reviewRepository.findById(idReview).
                orElseThrow(
                        () -> new IllegalArgumentException("Review con id: " + idReview + " non trovata")
                );
        comment.setReview(review);
        return CommentMapper.mapToDTO(commentRepository.save(comment));
    }

}
