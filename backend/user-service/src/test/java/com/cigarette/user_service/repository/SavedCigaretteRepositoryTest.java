package com.cigarette.user_service.repository;

import com.cigarette.user_service.entity.SavedCigarette;
import com.cigarette.user_service.util.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SavedCigaretteRepositoryTest {

    @Autowired
    private SavedCigaretteRepository repository;

    @Test
    void SavedCigaretteRepository_save_returnSaved(){
        SavedCigarette sc = SavedCigarette.builder()
                .idUser(UUID.randomUUID())
                .idCigarette(1L)
                .status(Status.LIKED)
                .createdAt(LocalDateTime.now())
                .build();

        SavedCigarette savedSavedCigarette = repository.save(sc);

        Assertions.assertThat(savedSavedCigarette).isNotNull();
        Assertions.assertThat(savedSavedCigarette.getId()).isGreaterThan(0);
    }
}
