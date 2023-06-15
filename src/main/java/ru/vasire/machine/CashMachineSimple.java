package ru.vasire.machine;

import lombok.NonNull;

import java.util.*;

/**
 * Simplified ATM
 */
public class CashMachineSimple implements CashMachine {
    private final SortedMap<Banknote, MoneyCell> moneyCells;

    /**
     * Simplified ATM
     *
     * @param cells Cells with banknotes. Cell values must be unique.
     */
    public CashMachineSimple(@NonNull MoneyCell... cells) {
        this.moneyCells = new TreeMap<>(Collections.reverseOrder(new Banknote.BanknoteComparator()));
        for (MoneyCell moneyCell : cells) {
            if (this.moneyCells.containsKey(moneyCell.getBanknote())) {
                throw new IllegalArgumentException("There can be only one cell with each denomination");
            }
            this.moneyCells.put(moneyCell.getBanknote(), moneyCell);
        }
    }

    /**
     * Returns the balance of funds
     *
     * @return balance of funds
     */
    @Override
    public int checkBalance() {
        return moneyCells.values().stream().mapToInt(MoneyCell::getBalance).sum();
    }

    /**
     * Cash withdrawal. Issues the requested amount with the minimum number of banknotes or an error if the amount cannot be issued
     *
     * @param requereSum requested amount
     * @return Banknotes corresponding to the requested amount
     * @throws InsufficientFundsException error if the amount cannot be issued
     */
    @Override
    public List<Banknote> getMoney(int requereSum) throws InsufficientFundsException {
        if (requereSum < 0) {
            throw new InsufficientFundsException("The requested amount cannot be negative");
        }
        List<Banknote> money = new ArrayList<>();
        for (MoneyCell moneyCell : moneyCells.values()) {
            money.addAll(moneyCell.getPossible(requereSum - Banknote.getBanknoteSum(money)));
            if (Banknote.getBanknoteSum(money) == requereSum)
                return money;
        }
        // TODO: Must be implemented by rolling back the transaction
        try {
            putMoney(money.toArray(Banknote[]::new));
        } catch (AcceptingFundsException e) {
            throw new RuntimeException(e);
        }
        throw new InsufficientFundsException("Unable to withdraw the specified amount");
    }

    /**
     * Accept banknotes of different denominations (each denomination must have its own cell)
     *
     * @param banknotes banknotes of different denominations
     * @return Funds accepted
     * @throws AcceptingFundsException Banknote acceptance error
     */
    @Override
    public int putMoney(@NonNull Banknote... banknotes) throws AcceptingFundsException {
        for (Banknote banknote : banknotes) {
            if (!moneyCells.containsKey(banknote)) {
                throw new AcceptingFundsException("Unable to accept banknote " + banknote);
            }
        }
        for (Banknote banknote : banknotes) {
            moneyCells.get(banknote).putOne();
        }
        return Banknote.getBanknoteSum(banknotes);
    }
}
