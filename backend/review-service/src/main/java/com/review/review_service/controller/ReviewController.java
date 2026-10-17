package com.review.review_service.controller;

import com.review.review_service.dto.ReviewRequestDTO;
import com.review.review_service.dto.ReviewResponseDTO;
import com.review.review_service.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long id){
        ReviewResponseDTO review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @PostMapping(path = "")
    public ResponseEntity<ReviewResponseDTO> createReview(
            @RequestBody ReviewRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt) {
        ReviewResponseDTO review = reviewService.createReview(dto, jwt);
        return ResponseEntity.ok(review);
    }

    @PutMapping(path = "/{idReview}")
    public ResponseEntity<ReviewResponseDTO> updateReview(
            @PathVariable Long idReview,
            @RequestBody ReviewRequestDTO dto,
            @AuthenticationPrincipal Jwt jwt
    )
    {
        ReviewResponseDTO review = reviewService.updateReview(idReview, dto, jwt);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping(path = "/{idReview}")
    public ResponseEntity<ReviewResponseDTO> deleteReview(
            @PathVariable Long idReview,
            @AuthenticationPrincipal Jwt jwt
        )
    {
        reviewService.deleteReview(idReview, jwt);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get all reviews of a cigarette
     * @param idCigarette
     * @return list of reviews
     */
    @GetMapping(path = "/cigarette/{idCigarette}")
    public ResponseEntity<List<ReviewResponseDTO>> getAllReviewsFromCig(@PathVariable Long idCigarette){
        List<ReviewResponseDTO> reviews = reviewService.getAllReviewsFromCig(idCigarette);
        return ResponseEntity.ok(reviews);
    }

}
