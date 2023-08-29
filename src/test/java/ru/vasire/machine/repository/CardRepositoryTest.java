package ru.vasire.machine.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.vasire.Main;
import ru.vasire.machine.model.Card;

import java.math.BigInteger;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@Testcontainers
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = {CardRepositoryTest.Initializer.class}, classes = Main.class)
@ActiveProfiles("test")
class CardRepositoryTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13");
    @Autowired
    CardRepository cardRepository;

    @Test
    void updatePinCode() {
        cardRepository.updatePinCode(1L, "4321");
        assertEquals("4321", cardRepository.findById(BigInteger.valueOf(1)).get().getPinCode());
    }

    @Test
    void findByCardNumber() {
        Optional<Card> cardOpt = cardRepository.findByCardNumber("12345");
        assertTrue(cardOpt.isPresent());
        Card card = cardOpt.get();
        assertEquals("12345", card.getCardNumber());
        assertEquals("1234", card.getPinCode());
        assertEquals("123-456", card.getAccountId());
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}