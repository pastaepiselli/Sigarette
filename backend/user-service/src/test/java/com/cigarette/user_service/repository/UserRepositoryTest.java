package com.cigarette.user_service.repository;

import com.cigarette.user_service.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void UserRepository_save_returnUser(){

        // arrange
        User user = User.builder()
                .id(UUID.randomUUID())
                .username("gamerxx")
                .build();

        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    }
}
