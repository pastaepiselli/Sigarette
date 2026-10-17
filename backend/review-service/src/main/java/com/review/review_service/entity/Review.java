package com.review.review_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "review")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user")
    private UUID idUser;

    @Column(name = "id_cigarette")
    private Long idCigarette;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "description")
    private String description;

    @Column(name = "dt_inserimento")
    private LocalDateTime dtInserimento;
}
