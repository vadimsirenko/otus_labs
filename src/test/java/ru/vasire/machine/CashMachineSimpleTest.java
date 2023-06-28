package ru.vasire.machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CashMachineSimpleTest {
    private CashMachineSimple cashMachine;

    @BeforeEach
    void init() {

        cashMachine = new CashMachineSimple(
                new BanknoteCell(Banknote.N100, 5),
                new BanknoteCell(Banknote.N500, 5),
                new BanknoteCell(Banknote.N1000, 5),
                new BanknoteCell(Banknote.N5000, 5));
    }

    @Test
    void getMoney() {

        List<Banknote> expectedList = List.of(Banknote.N100, Banknote.N500, Banknote.N1000, Banknote.N5000);
        int moneyToGet = 100 + 500 + 1000 + 5000;
        int initSum = cashMachine.checkBalance();

        List<Banknote> testList = cashMachine.getMoney(moneyToGet);
        assertEquals(moneyToGet, Banknote.getAmountOfBanknotes(testList));
        assertEquals(expectedList.size(), testList.size());
        assertEquals(cashMachine.checkBalance(), initSum - Banknote.getAmountOfBanknotes(testList));
    }

    @Test
    void getMoneyWhenMoneyNotEnoughRaiseException() {

        int initSum = cashMachine.checkBalance();
        int moneyToGet = 50_000;

        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> {
            cashMachine.getMoney(moneyToGet);
        });

        assertEquals("Unable to withdraw the specified amount", exception.getMessage());

        // The amount in the ATM should not change
        assertEquals(initSum, cashMachine.checkBalance());
    }

    @Test
    void putMoney() {
        List<Banknote> putBanknotes = List.of(Banknote.N100, Banknote.N500, Banknote.N1000, Banknote.N5000);
        int initSum = cashMachine.checkBalance();
        int expectedMoney = 100 + 500 + 1000 + 5000;

        cashMachine.putMoney(putBanknotes);
        assertEquals(initSum + Banknote.getAmountOfBanknotes(putBanknotes), cashMachine.checkBalance());
    }

    @Test
    void putMoneyWithAcceptingFundsException() {
        cashMachine = new CashMachineSimple(
                new BanknoteCell(Banknote.N100, 5),
                new BanknoteCell(Banknote.N1000, 5),
                new BanknoteCell(Banknote.N5000, 5));

        List<Banknote> putBanknotes = List.of(Banknote.N100, Banknote.N500, Banknote.N1000, Banknote.N5000);
        int initSum = cashMachine.checkBalance();

        AcceptingFundsException exception = assertThrows(AcceptingFundsException.class, () -> {
            cashMachine.putMoney(putBanknotes);
        });

        assertEquals("Unable to accept banknote N500", exception.getMessage());

        // The amount in the ATM should not change
        assertEquals(initSum, cashMachine.checkBalance());
    }

    @Test
    void checkBalance() {
        int expectedSum = 33_000;
        assertEquals(expectedSum, cashMachine.checkBalance());
    }

}