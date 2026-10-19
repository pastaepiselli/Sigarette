package com.review.review_service.events;


import com.review.review_service.util.ReviewEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEvent implements Serializable {
    private Long idCigarette;
    private Float rating;
    private Float oldRating;
    private ReviewEventType reviewEventType;
}
