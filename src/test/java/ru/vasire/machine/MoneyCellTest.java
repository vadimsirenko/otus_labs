package ru.vasire.machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyCellTest {
    private MoneyCell moneyCell;
    @BeforeEach
    void init() {
        moneyCell = new MoneyCell(Banknote.N100, 5);
    }
    @Test
    void getBalance() {
        int expectedSum = 500;
        assertEquals(expectedSum, moneyCell.getBalance());
    }

    @Test
    void getBanknote() {
        Banknote expectedBanknote = Banknote.N100;
        assertEquals(expectedBanknote, moneyCell.getBanknote());
    }

    @Test
    void getPossible() {
        int requeredSum = 350;
        int expectedNum = 300;
        assertEquals(expectedNum, Banknote.getBanknoteSum(moneyCell.getPossible(requeredSum)));
    }
    @Test
    void getPossibleIfLessThanCould() {
        int requeredSum = 750;
        int expectedNum = 500;
        assertEquals(expectedNum, Banknote.getBanknoteSum(moneyCell.getPossible(requeredSum)));
    }
    @Test
    void getPossibleThereAreNoBanknotes() {
        int requeredSum = 350;
        int expectedNum = 0;
        moneyCell = new MoneyCell(Banknote.N100, 0);
        assertEquals(expectedNum, Banknote.getBanknoteSum(moneyCell.getPossible(requeredSum)));
    }

    @Test
    void putOne() {
        int initBalance = moneyCell.getBalance();
        int expectedDiff = 100;
        moneyCell.putOne();
        assertEquals(expectedDiff, moneyCell.getBalance()-initBalance);
    }

    @Test
    void putBanknotes() {
        int initBalance = moneyCell.getBalance();
        int expectedDiff = 5*100;
        moneyCell.putBanknotes(5);
        assertEquals(expectedDiff, moneyCell.getBalance()-initBalance);
    }
}