package ru.vasire.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vasire.machine.exception.InvalidAccountException;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.repository.AccountCardRepository;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    private AccountService service;

    @BeforeEach
    void init() {
        AccountCardRepository accountCardRepository = new AccountCardRepository(new HashSet<>());

        Account account = accountCardRepository.addAccount("123-456", BigDecimal.valueOf(12345.12));
        account.addCard("12345", "1234");

        account = accountCardRepository.addAccount("234-567", BigDecimal.valueOf(23456.23));
        account.addCard("23456", "2345");

        account = accountCardRepository.addAccount("345-678", BigDecimal.valueOf(34567.34));
        account.addCard("34567", "3456");


        service = new AccountServiceImpl(accountCardRepository);
    }

    @Test
    void getAccountByCardNumberRightParams() {
        Account expectedAccount = new Account("123-456", BigDecimal.valueOf(12345.12));
        Account testAccount = service.getAccountByCardNumber("12345");

        assertEquals(expectedAccount, testAccount);
    }

    @Test
    void getAccountByWrongCardNumber() {
        var testAccount = service.getAccountByCardNumber("12345!");
        assertNull(testAccount);
    }

    @Test
    void getBalanceByCardNumber() {
        BigDecimal expectedBalance = BigDecimal.valueOf(12345.12);
        BigDecimal testBalance = service.getBalanceByCardNumber("12345");

        assertEquals(expectedBalance, testBalance);
    }

    @Test
    void getBalanceByCardNumberIncorrectAccount() {
        BigDecimal expectedBalance = BigDecimal.valueOf(12345.12);

        InvalidAccountException exception = assertThrows(InvalidAccountException.class, () -> {
            BigDecimal testBalance = service.getBalanceByCardNumber("1234!");
        });
        assertEquals("Invalid account", exception.getMessage());
    }

    @Test
    void changeBalancePositiveChange() {

        String cardNumber = "12345";
        BigDecimal deltaBalance = new BigDecimal(25.25);

        BigDecimal initBalance = service.getBalanceByCardNumber(cardNumber);
        BigDecimal expectedBalance = initBalance.add(deltaBalance);

        service.changeBalance(cardNumber, deltaBalance);

        BigDecimal testBalance = service.getBalanceByCardNumber(cardNumber);

        assertEquals(expectedBalance, testBalance);

        // 12345.12 +25.25 = 12370.37
        assertEquals(BigDecimal.valueOf(12370.37), testBalance);

    }

    @Test
    void changeBalanceNegativeChange() {

        String cardNumber = "12345";
        BigDecimal deltaBalance = new BigDecimal(-25.25);

        BigDecimal initBalance = service.getBalanceByCardNumber(cardNumber);
        BigDecimal expectedBalance = initBalance.add(deltaBalance);

        service.changeBalance(cardNumber, deltaBalance);

        BigDecimal testBalance = service.getBalanceByCardNumber(cardNumber);

        assertEquals(expectedBalance, testBalance);

        // 12345.12 -25.25 = 12319,87
        assertEquals(BigDecimal.valueOf(12319.87), testBalance);
    }
}