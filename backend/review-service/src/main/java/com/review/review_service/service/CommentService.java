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

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public CommentResponseDTO getCommentById(Long idReview, Long id){
        return commentRepository.findByIdAndReviewId(idReview, id)
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

    public CommentResponseDTO updateComment(Long idReview, Long id, CommentRequestDTO dto){
        Comment comment = commentRepository.findByIdAndReviewId(id, idReview).orElseThrow(
                () -> new IllegalArgumentException("Comment con id: " + id + " non trovato")
        );
        comment.setDescription(dto.description());
        comment.setDtInserimento(LocalDateTime.now());
        return CommentMapper.mapToDTO(commentRepository.save(comment));
    }


    public void deleteComment(Long idReview, Long id){
        Comment comment = commentRepository.findByIdAndReviewId(id, idReview).orElseThrow(
                () -> new IllegalArgumentException("Comment with id: " + id + " not find")
        );
        commentRepository.delete(comment);
    }



}
