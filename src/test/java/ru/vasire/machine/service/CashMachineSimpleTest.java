package ru.vasire.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vasire.machine.exception.AcceptingFundsException;
import ru.vasire.machine.exception.InsufficientFundsException;
import ru.vasire.machine.model.Banknote;
import ru.vasire.machine.model.BanknoteCell;
import ru.vasire.machine.service.CashMachineSimple;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CashMachineSimpleTest {
    private CashMachineSimple cashMachine;

    @BeforeEach
    void init() {

        cashMachine = new CashMachineSimple(Arrays.asList(
                new BanknoteCell(Banknote.N100, 5),
                new BanknoteCell(Banknote.N500, 5),
                new BanknoteCell(Banknote.N1000, 5),
                new BanknoteCell(Banknote.N5000, 5)));
    }

    @Test
    void getMoney() {

        List<Banknote> expectedList = List.of(Banknote.N100, Banknote.N500, Banknote.N1000, Banknote.N5000);
        int moneyToGet = 100 + 500 + 1000 + 5000;
        int initSum = cashMachine.getBalance();

        List<Banknote> testList = cashMachine.getMoney(moneyToGet);
        assertEquals(moneyToGet, Banknote.getAmountOfBanknotes(testList));
        assertEquals(expectedList.size(), testList.size());
        assertEquals(cashMachine.getBalance(), initSum - Banknote.getAmountOfBanknotes(testList));
    }

    @Test
    void getMoneyWhenMoneyNotEnoughRaiseException() {

        int initSum = cashMachine.getBalance();
        int moneyToGet = 50_000;

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> {
            cashMachine.getMoney(moneyToGet);
        });

        assertEquals("Unable to withdraw the specified amount", exception.getMessage());

        // The amount in the ATM should not change
        assertEquals(initSum, cashMachine.getBalance());
    }

    @Test
    void putMoney() {
        List<Banknote> putBanknotes = List.of(Banknote.N100, Banknote.N500, Banknote.N1000, Banknote.N5000);
        int initSum = cashMachine.getBalance();
        int expectedMoney = 100 + 500 + 1000 + 5000;

        cashMachine.putMoney(putBanknotes);
        assertEquals(initSum + Banknote.getAmountOfBanknotes(putBanknotes), cashMachine.getBalance());
    }

    @Test
    void putMoneyWithAcceptingFundsException() {
        cashMachine = new CashMachineSimple(Arrays.asList(
                new BanknoteCell(Banknote.N100, 5),
                new BanknoteCell(Banknote.N1000, 5),
                new BanknoteCell(Banknote.N5000, 5)));

        List<Banknote> putBanknotes = List.of(Banknote.N100, Banknote.N500, Banknote.N1000, Banknote.N5000);
        int initSum = cashMachine.getBalance();

        AcceptingFundsException exception = assertThrows(AcceptingFundsException.class, () -> {
            cashMachine.putMoney(putBanknotes);
        });

        assertEquals("Unable to accept banknote", exception.getMessage());

        // The amount in the ATM should not change
        assertEquals(initSum, cashMachine.getBalance());
    }

    @Test
    void checkBalance() {
        int expectedSum = 33_000;
        assertEquals(expectedSum, cashMachine.getBalance());
    }

}