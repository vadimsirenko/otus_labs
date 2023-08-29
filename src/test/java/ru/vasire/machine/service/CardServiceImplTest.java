package ru.vasire.machine.service;

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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest()
@Testcontainers
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = {CardServiceImplTest.Initializer.class}, classes = Main.class)
@ActiveProfiles("test")
class CardServiceImplTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13");
    @Autowired
    CardService cardService;

    @Test
    void verifyPinCode() {
        assertTrue(cardService.verifyPinCode("12345", "1234"));
        assertFalse(cardService.verifyPinCode("12345", "1234!"));
    }

    @Test
    void getBalance() {
        assertEquals(BigDecimal.valueOf(12345.12), cardService.getBalance("12345", "1234"));
    }

    @Test
    void changePinCode() {
        cardService.changePinCode("12345", "1234", "4321");
        assertTrue(cardService.verifyPinCode("12345", "4321"));
    }

    @Test
    void changeBalance() {
        cardService.changeBalance("12345", "1234", BigDecimal.valueOf(10_000));
        assertEquals(BigDecimal.valueOf(22345.12), cardService.getBalance("12345", "1234"));
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