package it.cigbase.sigarette.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name ="description")
    private String description;

    @Column(name = "logo_url")
    private String logoUrl;
}

