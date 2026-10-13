package com.review.review_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "comment")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_review", referencedColumnName = "id")
    private Review Review;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "dt_inserimento", nullable = false)
    private LocalDateTime dtInserimento;
}
