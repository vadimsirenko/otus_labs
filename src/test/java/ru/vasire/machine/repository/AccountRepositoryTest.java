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
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.Card;
import ru.vasire.machine.model.dto.CardDetailsDto;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@Testcontainers
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(initializers = {AccountRepositoryTest.Initializer.class}, classes = Main.class)
@ActiveProfiles("test")
class AccountRepositoryTest {
    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13");
    @Autowired
    AccountRepository accountRepository;

    @Test
    void findByCardNumber() {
        List<Account> accountList = accountRepository.findByCardNumber("12345");
        assertEquals(1, accountList.size());
        Account account = accountList.get(0);
        assertEquals(BigDecimal.valueOf(12345.12), account.getBalance());
        assertEquals("123-456", account.getNumber());
        assertEquals(1, account.getCards().size());
        Card card = account.getCards().stream().findFirst().get();
        assertEquals("12345", card.getCardNumber());
        assertEquals("1234", card.getPinCode());
        assertEquals("123-456", card.getAccountId());
    }

    @Test
    void findAllAccountCards() {
        List<CardDetailsDto> cardDetailsDtoList = accountRepository.findAllAccountCards();
        assertEquals(3, cardDetailsDtoList.size());
        CardDetailsDto cardDetailsDto = cardDetailsDtoList.get(0);
        assertEquals(BigDecimal.valueOf(12345.12), cardDetailsDto.getBalance());
        assertEquals("123-456", cardDetailsDto.getAccuntNumber());
        assertEquals("12345", cardDetailsDto.getCardNumber());
        assertEquals("1234", cardDetailsDto.getPinCode());
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
