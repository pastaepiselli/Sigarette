package com.review.review_service.controller;

import com.review.review_service.dto.CommentRequestDTO;
import com.review.review_service.dto.CommentResponseDTO;
import com.review.review_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable Long idReview, @RequestBody CommentRequestDTO dto){
        CommentResponseDTO comment = commentService.createComment(idReview, dto);
        return ResponseEntity.ok(comment);
    }

    // TODO: PUT, DELETE, GET all commenti from a review

}
