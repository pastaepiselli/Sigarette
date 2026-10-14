package it.cigbase.sigarette.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cigarette")
public class Cigarette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "nicotine_mg")
    private Float nicotine;

    @Column(name = "tar_mg")
    private Float tar;

    @Column(name = "co_mg")
    private Float co2;

    @Column(name = "format")
    private String format;

    @Column(name = "sticks_per_pack")
    private Integer sticksTot;

    @Column(name = "is_methol", nullable = false)
    private Boolean isMenthol;

    @Column(name = "avg_rating", nullable = false)
    private Float avgRating;

    @Column(name = "ratings_count", nullable = false)
    private Integer ratingsCount;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
