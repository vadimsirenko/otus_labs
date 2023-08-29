package ru.vasire.machine.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BanknoteTest {
    private Banknote banknote;

    @BeforeEach
    void init() {
        banknote = Banknote.N1000;
    }

    @Test
    void getBanknoteSum() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(Banknote.N5000);
        banknotes.add(Banknote.N1000);
        banknotes.add(Banknote.N500);
        banknotes.add(Banknote.N100);
        int expectedSum = 6600;
        assertEquals(expectedSum, Banknote.getAmountOfBanknotes(banknotes));
    }

    @Test
    void testGetBanknoteSumEmptyList() {
        List<Banknote> banknotes = new ArrayList<>();
        int expectedSum = 0;
        assertEquals(expectedSum, Banknote.getAmountOfBanknotes(banknotes));
    }

    @Test
    void getSumOfNumberBanknote() {
        int banknoteNum = 5;
        int expectedSum = 5_000;
        assertEquals(expectedSum, banknote.getAmountOfNumberBanknote(banknoteNum));
    }

    @Test
    void maxBanknoteCountLessOrEqToSum() {
        int requeredSum = 5_000;
        int expectedNumber = 5;
        assertEquals(expectedNumber, banknote.MaxBanknoteCountLessOrEqToSum(requeredSum));
    }

    @Test
    void maxBanknoteCountLessOrEqToSumLess() {
        int requeredSum = 5_100;
        int expectedNumber = 5;
        assertEquals(expectedNumber, banknote.MaxBanknoteCountLessOrEqToSum(requeredSum));
    }

    @Test
    void maxBanknoteCountLessOrEqToSumZero() {
        int requeredSum = 0;
        int expectedNumber = 0;
        assertEquals(expectedNumber, banknote.MaxBanknoteCountLessOrEqToSum(requeredSum));
    }
}