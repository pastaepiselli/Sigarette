package com.review.review_service.service;

import com.review.review_service.dto.ReviewRequestDTO;
import com.review.review_service.dto.ReviewResponseDTO;
import com.review.review_service.entity.Review;
import com.review.review_service.mapper.ReviewMapper;
import com.review.review_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewResponseDTO getReviewById(Long id){
        return  reviewRepository.findById(id)
                .map(ReviewMapper::mapToDTO)
                .orElseThrow(
                        () -> new IllegalArgumentException("Review with id: " + id + " not found")
                );
    }

    public ReviewResponseDTO createReview(ReviewRequestDTO dto){
        Review review = reviewRepository.save(ReviewMapper.mapToEntity(dto));
        return ReviewMapper.mapToDTO(review);
    }

    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO dto){
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review with id: " + id + " not found")
        );
        review.setDescription(dto.description());
        review.setDtInserimento(LocalDateTime.now());
        review.setRating(dto.rating());

        Review updatedReview = reviewRepository.save(review);
        return ReviewMapper.mapToDTO(updatedReview);
    }

    public void deleteReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review with id: " + id + " not found")
        );
        reviewRepository.delete(review);
    }

    public List<ReviewResponseDTO> getAllReviewsFromCig(Long idCigarette){
        return reviewRepository.findAllByIdCigarette(idCigarette).stream()
                .map(ReviewMapper::mapToDTO)
                .toList();
    }


}
