package com.review.review_service.service;

import com.review.review_service.dto.CommentRequestDTO;
import com.review.review_service.dto.CommentResponseDTO;
import com.review.review_service.entity.Comment;
import com.review.review_service.entity.Review;
import com.review.review_service.mapper.CommentMapper;
import com.review.review_service.repository.CommentRepository;
import com.review.review_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public CommentResponseDTO getCommentById(Long idReview, Long id){
        return commentRepository.findByIdAndIdReview(idReview, id)
                .map(CommentMapper::mapToDTO)
                .orElseThrow(
                        () -> new IllegalArgumentException("Comment with id: " + id + " not found")
                );
    }

    public List<CommentResponseDTO> getAllCommentsOfReview(Long idReview){
        return commentRepository.findAllCommentsByIdReview(idReview).stream()
                .map(CommentMapper::mapToDTO)
                .toList();
    }

    public CommentResponseDTO createComment(Long idReview, CommentRequestDTO dto, Jwt jwt){
        Comment comment = CommentMapper.mapToEntity(dto);
        Review review = reviewRepository.findById(idReview).
                orElseThrow(
                        () -> new IllegalArgumentException("Review con id: " + idReview + " non trovata")
                );
        UUID uuid = UUID.fromString(jwt.getSubject());
        comment.setReview(review);
        comment.setIdUser(uuid);
        comment.setUpdatedAt(LocalDateTime.now());
        return CommentMapper.mapToDTO(commentRepository.save(comment));
    }

    public CommentResponseDTO updateComment(Long idReview, Long idComment, CommentRequestDTO dto, Jwt jwt){
        UUID idUser = UUID.fromString(jwt.getSubject());
        Comment comment = commentRepository.findMyCommentById(idReview, idComment, idUser).orElseThrow(
                () -> new IllegalArgumentException("Comment with id: " + idComment + " not found")
        );

        comment.setDescription(dto.description());
        comment.setUpdatedAt(LocalDateTime.now());

        return CommentMapper.mapToDTO(commentRepository.save(comment));
    }


    public void deleteComment(Long idReview, Long idComment, Jwt jwt){

        UUID idUser = UUID.fromString(jwt.getSubject());
        Comment comment = commentRepository.findMyCommentById(idReview, idComment, idUser).orElseThrow(
                () -> new IllegalArgumentException("Comment with id: " + idComment + " not found")
        );
        commentRepository.delete(comment);
    }
}
