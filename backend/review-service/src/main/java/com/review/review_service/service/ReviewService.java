package com.review.review_service.service;

import com.review.review_service.dto.ReviewRequestDTO;
import com.review.review_service.dto.ReviewResponseDTO;
import com.review.review_service.entity.Review;
import com.review.review_service.events.ReviewEvent;
import com.review.review_service.mapper.ReviewMapper;
import com.review.review_service.repository.ReviewRepository;
import com.review.review_service.util.ReviewEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${spring.activemq.queue}")
    private String queue;

    public ReviewResponseDTO getReviewById(Long id){
        return reviewRepository.findById(id)
                .map(ReviewMapper::mapToDTO)
                .orElseThrow(
                        () -> new IllegalArgumentException("Review with id: " + id + " not found")
                );
    }

    @Transactional
    public ReviewResponseDTO createReview(ReviewRequestDTO dto, Jwt jwt){
        Review review = ReviewMapper.mapToEntity(dto);
        UUID idUser = UUID.fromString(jwt.getSubject());
        review.setIdUser(idUser);
        // questo metodo invia il mess convertendolo in jms object
        ReviewEvent reviewEvent = new ReviewEvent(
                dto.idCigarette(),
                dto.rating(),
                nullp,
                ReviewEventType.REVIEW_EVENT_CREATED);
        jmsTemplate.convertAndSend(queue, reviewEvent);
        return ReviewMapper.mapToDTO(reviewRepository.save(review));
    }

    @Transactional
    public ReviewResponseDTO updateReview(Long idReview, ReviewRequestDTO dto, Jwt jwt){

        // devo trovare la mia review
        UUID uuid = UUID.fromString(jwt.getSubject());
        Review review = reviewRepository.findMyReviewWithId(idReview, uuid).orElseThrow(
                () -> new IllegalArgumentException("Review with id: " + idReview + " of this user not found")
        );

        Float oldRating = review.getRating();
        review.setRating(dto.rating());
        review.setDescription(dto.description());
        review.setUpdatedAt(LocalDateTime.now());

        ReviewEvent reviewEvent = new ReviewEvent(
                review.getIdCigarette(),
                dto.rating(),
                oldRating,
                ReviewEventType.REVIEW_EVENT_UPDATED);
        jmsTemplate.convertAndSend(queue, reviewEvent);

        Review savedReview = reviewRepository.save(review);
        return ReviewMapper.mapToDTO(savedReview);
    }

    public void deleteReview(Long idReview, Jwt jwt){
        UUID idUser = UUID.fromString(jwt.getSubject());
        Review review = reviewRepository.findMyReviewWithId(idReview, idUser).orElseThrow(
                () -> new IllegalArgumentException("Review with id: " + idReview + " of this user not found")
        );

        ReviewEvent reviewEvent =  new ReviewEvent(
                review.getIdCigarette(),
                review.getRating(),
                null,
                ReviewEventType.REVIEW_EVENT_DELETED
        );
        jmsTemplate.convertAndSend(queue, reviewEvent);
        reviewRepository.delete(review);
    }

    public List<ReviewResponseDTO> getAllReviewsFromCig(Long idCigarette){
        return reviewRepository.findAllByIdCigarette(idCigarette).stream()
                .map(ReviewMapper::mapToDTO)
                .toList();
    }


}
