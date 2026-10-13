package com.review.review_service.controller;

import com.review.review_service.dto.ReviewRequestDTO;
import com.review.review_service.dto.ReviewResponseDTO;
import com.review.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long id){
        ReviewResponseDTO review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @PostMapping(path = "")
    public ResponseEntity<ReviewResponseDTO> createReview(@RequestBody ReviewRequestDTO dto){
        ReviewResponseDTO review = reviewService.createReview(dto);
        return ResponseEntity.ok(review);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ReviewResponseDTO> updateReview(@PathVariable Long id, @RequestBody ReviewRequestDTO dto){
        ReviewResponseDTO review = reviewService.updateReview(id, dto);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ReviewResponseDTO> deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}
