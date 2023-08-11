package ru.vasire.machine.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.Card;
import ru.vasire.machine.repository.AccountCardRepository;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CardServiceImplTest {
    private CardService cardService;
    private AccountService accountService;

    @BeforeEach
    void init() {
        AccountCardRepository accountCardRepository = new AccountCardRepository(new HashSet<>());

        Account account = accountCardRepository.addAccount("123-456", BigDecimal.valueOf(12345.12));
        account.addCard("12345", "1234");

        account = accountCardRepository.addAccount("234-567", BigDecimal.valueOf(23456.23));
        account.addCard("23456", "2345");

        account = accountCardRepository.addAccount("345-678", BigDecimal.valueOf(34567.34));
        account.addCard("34567", "3456");


        accountService = new AccountServiceImpl(accountCardRepository);
        cardService = new CardServiceImpl(accountService, accountCardRepository);
    }

    @Test
    void verifyPinCode() {
        Assertions.assertTrue(cardService.verifyPinCode("12345", "1234"));
    }

    @Test
    void verifyWrongPinCode() {
        Assertions.assertFalse(cardService.verifyPinCode("12345", "1234!"));
    }

    @Test
    void getBalance() {
        Assertions.assertEquals(BigDecimal.valueOf(12345.12), cardService.getBalance("12345", "1234"));
    }

    @Test
    void changePinCode() {
        Assertions.assertTrue(cardService.changePinCode("12345", "1234", "1234!"));
        Assertions.assertEquals("1234!", ((Card)accountService.getAccountByCardNumber("12345").getCards().stream().toArray()[0]).getPinCode());
    }

    @Test
    void changeBalance() {
        String cardNumber = "12345";
        String pinCode = "1234";
        BigDecimal deltaBalance = new BigDecimal(25.25);

        BigDecimal initBalance = cardService.getBalance(cardNumber, pinCode);
        BigDecimal expectedBalance = initBalance.add(deltaBalance);

        cardService.changeBalance(cardNumber, pinCode, deltaBalance);

        BigDecimal testBalance = cardService.getBalance(cardNumber, pinCode);

        assertEquals(expectedBalance, testBalance);

        // 12345.12 +25.25 = 12370.37
        assertEquals(BigDecimal.valueOf(12370.37), testBalance);
    }
}