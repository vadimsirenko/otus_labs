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
import ru.vasire.machine.model.Account;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@Testcontainers
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = {AccountServiceImplTest.Initializer.class}, classes = Main.class)
@ActiveProfiles("test")
class AccountServiceImplTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13");
    @Autowired
    AccountService accountService;

    @Test
    void getAccountByCardNumber() {
        Account account = accountService.getAccountByCardNumber("12345");
        assertEquals("123-456", account.getNumber());
    }

    @Test
    void getBalanceByCardNumber() {
        Account account = accountService.getAccountByCardNumber("12345");
        assertEquals(BigDecimal.valueOf(12345.12), account.getBalance());
    }

    @Test
    void changeBalance() {
        accountService.changeBalance("12345", BigDecimal.valueOf(10_000));
        Account account = accountService.getAccountByCardNumber("12345");
        assertEquals(BigDecimal.valueOf(22345.12), account.getBalance());
    }

    @Test
    void getCardDetails() {
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