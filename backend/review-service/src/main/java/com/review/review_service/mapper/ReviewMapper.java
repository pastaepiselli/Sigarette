package com.review.review_service.mapper;

import com.review.review_service.dto.ReviewRequestDTO;
import com.review.review_service.dto.ReviewResponseDTO;
import com.review.review_service.entity.Review;

import java.time.LocalDateTime;

public class ReviewMapper {

    public static ReviewResponseDTO mapToDTO(Review review){
        return new ReviewResponseDTO(
                review.getId(),
                review.getIdUser(),
                review.getIdCigarette(),
                review.getRating(),
                review.getDescription(),
                review.getDtInserimento()
        );
    }

    public static Review mapToEntity(ReviewRequestDTO dto){
        return new Review(
                null,
                null,
                dto.idCigarette(),
                dto.rating(),
                dto.description(),
                LocalDateTime.now()
        );
    }
}
