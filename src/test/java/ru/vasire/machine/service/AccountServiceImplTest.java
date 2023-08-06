package ru.vasire.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vasire.machine.exception.InvalidAccountException;
import ru.vasire.machine.model.Account;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceImplTest {

    private AccountService service;

    @BeforeEach
    void init() {

        service = new AccountServiceImpl();
    }

    @Test
    void getAccountByCardNumberRightParams() {
        Account expectedAccount = new Account("12345", "1234");
        Account testAccount = service.getAccountByCardNumber(expectedAccount.getCardNumber());

        assertEquals(expectedAccount, testAccount);
    }

    @Test
    void getAccountByCardNumberWithAnError() {
        InvalidAccountException exception = assertThrows(InvalidAccountException.class, () -> {
            service.getAccountByCardNumber("12345!");
        });
        assertEquals("Invalid card number or PIN code", exception.getMessage());
    }


    @Test
    void getBalance() {
        BigDecimal expectedBalance = BigDecimal.valueOf(12345.12);
        BigDecimal testBalance = service.getBalance(new Account("12345", "1234"));

        assertEquals(expectedBalance, testBalance);
    }

    @Test
    void getBalanceIncorrectAccount() {
        BigDecimal expectedBalance = BigDecimal.valueOf(12345.12);

        InvalidAccountException exception = assertThrows(InvalidAccountException.class, () -> {
            BigDecimal testBalance = service.getBalance(new Account("12345", "1234!"));
        });
        assertEquals("Invalid account", exception.getMessage());
    }

    @Test
    void changeBalancePositiveChange() {

        Account testAccount = new Account("12345", "1234");
        BigDecimal deltaBalance = new BigDecimal(25.25);

        BigDecimal initBalance = service.getBalance(testAccount);
        BigDecimal expectedBalance = initBalance.add(deltaBalance);

        service.changeBalance(testAccount, deltaBalance);

        BigDecimal testBalance = service.getBalance(testAccount);

        assertEquals(expectedBalance, testBalance);

        // 12345.12 +25.25 = 12370.37
        assertEquals(BigDecimal.valueOf(12370.37),testBalance);

    }

    @Test
    void changeBalanceNegativeChange() {

        Account testAccount = new Account("12345", "1234");
        BigDecimal deltaBalance = new BigDecimal(-25.25);

        BigDecimal initBalance = service.getBalance(testAccount);
        BigDecimal expectedBalance = initBalance.add(deltaBalance);

        service.changeBalance(testAccount, deltaBalance);

        BigDecimal testBalance = service.getBalance(testAccount);

        assertEquals(expectedBalance, testBalance);

        // 12345.12 -25.25 = 12319,87
        assertEquals(BigDecimal.valueOf(12319.87),testBalance);
    }
}