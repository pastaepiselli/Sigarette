package com.review.review_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "comment", schema = "review")
@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_review", referencedColumnName = "id")
    private Review idReview;

    @Column(name = "description", nullable = false)
    private String description;
}
