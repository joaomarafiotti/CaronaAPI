package br.ifsp.demo.integration.persistence;

import br.ifsp.demo.domain.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(br.ifsp.demo.config.TestConfig.class)
@ActiveProfiles("test")
@DisplayName("Car Persistence Tests")
class CarPersistenceTest {

    @Autowired
    private TestEntityManager entityManager;

    private Driver driver;
    private Driver anotherDriver;

    @BeforeEach
    void setUp() {
        entityManager.clear();

        driver = createDriver("Fulano", "Silva", "fulano.silva@gmail.com", "005.046.860-03");
        anotherDriver = createDriver("Ciclano", "Souza", "ciclano.souza@outlook.com", "355.553.060-75");
    }
}
