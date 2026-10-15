package com.review.review_service.controller;

import com.review.review_service.dto.CommentRequestDTO;
import com.review.review_service.dto.CommentResponseDTO;
import com.review.review_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/reviews/{idReview}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommentResponseDTO> getCommentById(@PathVariable Long idReview, @PathVariable Long id){
        CommentResponseDTO comment = commentService.getCommentById(idReview, id);
        return ResponseEntity.ok(comment);
    }

    @GetMapping(path = "")
    public ResponseEntity<List<CommentResponseDTO>> getAllCommentsOfReview(@PathVariable Long idReview){
       List<CommentResponseDTO> comments = commentService.getAllCommentsOfReview(idReview);
       return ResponseEntity.ok(comments);
    }

    @PostMapping(path = "")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long idReview, @RequestBody CommentRequestDTO dto){
        CommentResponseDTO comment = commentService.createComment(idReview, dto);
        return ResponseEntity.ok(comment);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable Long idReview,
            @PathVariable Long id,
            @RequestBody CommentRequestDTO dto){
        CommentResponseDTO comment = commentService.updateComment(idReview, id, dto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteComment(
            @PathVariable Long idReview,
            @PathVariable Long id
    )
    {
        commentService.deleteComment(idReview, id);
    }

}
