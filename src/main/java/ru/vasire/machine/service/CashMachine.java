package ru.vasire.machine.service;

import ru.vasire.machine.model.Banknote;
import ru.vasire.machine.exception.AcceptingFundsException;
import ru.vasire.machine.exception.InsufficientFundsException;

import java.util.List;

public interface CashMachine {

    /**
     * Returns the balance of funds
     *
     * @return balance of funds
     */
    int getBalance();

    /**
     * Cash withdrawal. Issues the requested amount with the minimum number of banknotes or an error if the amount cannot be issued
     *
     * @param requiredSum requested amount
     * @return Banknotes corresponding to the requested amount
     * @throws InsufficientFundsException error if the amount cannot be issued
     */
    List<Banknote> getMoney(int requiredSum);

    /**
     * Accept banknotes of different denominations (each denomination must have its own cell)
     *
     * @param banknotes banknotes of different denominations
     * @throws AcceptingFundsException Banknote acceptance error
     */
    void putMoney(List<Banknote> banknotes);
}
