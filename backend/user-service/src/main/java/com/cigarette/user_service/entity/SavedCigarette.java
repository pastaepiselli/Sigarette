package com.cigarette.user_service.entity;

import com.cigarette.user_service.util.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavedCigarette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user", nullable = false)
    private UUID idUser;

    @Column(name = "id_cigarette", nullable = false)
    private Long idCigarette;

    @Enumerated(EnumType.STRING) // salva il valore dell enum come stringa nel db
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
