package ru.vasire.machine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vasire.machine.exception.InsufficientFundsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BanknoteCellTest {
    private BanknoteCell banknoteCell;

    @BeforeEach
    void init() {
        banknoteCell = new BanknoteCell(Banknote.N100, 5);
    }

    @Test
    void getBalance() {
        int expectedSum = 500;
        assertEquals(expectedSum, banknoteCell.getBalance());
    }

    @Test
    void getBanknote() {
        Banknote expectedBanknote = Banknote.N100;
        assertEquals(expectedBanknote, banknoteCell.getBanknote());
    }

    @Test
    void requestForBanknotesMoreThanTheBalance() {
        int banknoteCountForRequest = banknoteCell.getBanknoteCount() + 1;
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> {
            banknoteCell.removeBanknotes(banknoteCountForRequest);
        });

        assertEquals("The requested number of banknotes is greater than the balance", exception.getMessage());
    }

    @Test
    void putBanknotes() {
        int initBalance = banknoteCell.getBalance();
        int expectedDiff = 5 * 100;
        banknoteCell.addBanknotes(5);
        assertEquals(expectedDiff, banknoteCell.getBalance() - initBalance);
    }
}