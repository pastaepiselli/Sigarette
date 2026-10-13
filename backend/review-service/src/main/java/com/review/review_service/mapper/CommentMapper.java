package com.review.review_service.mapper;


import com.review.review_service.dto.CommentRequestDTO;
import com.review.review_service.dto.CommentResponseDTO;
import com.review.review_service.entity.Comment;

import java.time.LocalDateTime;

public class CommentMapper {
    public static CommentResponseDTO mapToDTO(Comment comment){
        return new CommentResponseDTO(
                comment.getId(),
                ReviewMapper.mapToDTO(comment.getReview()),
                comment.getIdUser(),
                comment.getDescription(),
                comment.getDtInserimento()
        );
    }

    public static Comment mapToEntity(CommentRequestDTO dto){
        return new Comment(
                null,
                null,
                null,
                dto.description(),
                LocalDateTime.now()
        );
    }

}
