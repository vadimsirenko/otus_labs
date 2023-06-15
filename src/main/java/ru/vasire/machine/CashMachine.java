package ru.vasire.machine;

import java.util.List;

/**
 * ATM interface
 */
public interface CashMachine {

    /**
     * Returns the balance of funds
     * @return balance of funds
     */
    int checkBalance();

    /**
     * Cash withdrawal. Issues the requested amount with the minimum number of banknotes or an error if the amount cannot be issued
     * @param requereSum requested amount
     * @return Banknotes corresponding to the requested amount
     * @throws InsufficientFundsException  error if the amount cannot be issued
     */
    List<Banknote> getMoney(int requereSum) throws InsufficientFundsException;

    /**
     * Accept banknotes of different denominations (each denomination must have its own cell)
     * @param banknotes  banknotes of different denominations
     * @return Funds accepted
     * @throws AcceptingFundsException Banknote acceptance error
     */
    int putMoney(Banknote... banknotes) throws AcceptingFundsException;
}
