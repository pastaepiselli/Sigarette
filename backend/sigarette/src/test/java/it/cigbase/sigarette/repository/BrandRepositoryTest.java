package it.cigbase.sigarette.repository;

import it.cigbase.sigarette.entity.Brand;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    void BrandRepository_findAll_returnListBrand(){
        // setup
        Brand marboro = Brand.builder().name("Marboro").build();
        Brand camel = Brand.builder().name("Camel").build();

        brandRepository.saveAll(List.of(marboro, camel));

        // act
        List<Brand> brands = brandRepository.findAll();

        // assert
        Assertions.assertThat(brands).isNotNull();
        Assertions.assertThat(brands).hasSize(2);
    }
}
