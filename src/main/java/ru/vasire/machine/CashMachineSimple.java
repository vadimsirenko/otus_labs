package ru.vasire.machine;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;


public class CashMachineSimple implements CashMachine {
    private final SortedMap<Banknote, BanknoteCell> moneyCells;

    /**
     * Simplified ATM
     *
     * @param cells Cells with banknotes. Cell values must be unique.
     */
    public CashMachineSimple(@NonNull BanknoteCell... cells) {
        moneyCells = new TreeMap<>(Collections.reverseOrder(new Banknote.BanknoteComparator()));
        for (BanknoteCell banknoteCell : cells) {
            if (moneyCells.containsKey(banknoteCell.getBanknote())) {
                throw new IllegalArgumentException("There can be only one cell with each denomination");
            }
            moneyCells.put(banknoteCell.getBanknote(), banknoteCell);
        }
    }

    /**
     * Returns the balance of funds
     *
     * @return balance of funds
     */
    @Override
    public int getBalance() {
        return moneyCells.values().stream().mapToInt(BanknoteCell::getBalance).sum();
    }

    /**
     * Cash withdrawal. Issues the requested amount with the minimum number of banknotes or an error if the amount cannot be issued
     *
     * @param requereSum requested amount
     * @return Banknotes corresponding to the requested amount
     * @throws InsufficientFundsException error if the amount cannot be issued
     */
    @Override
    public List<Banknote> getMoney(int requereSum) {
        if (requereSum <= 0) {
            throw new InsufficientFundsException("Requested amount must be greater than zero");
        }
        List<Banknote> money = calculationBanknoteSet(requereSum);
        Map<Banknote, Integer> changeList = BanknoteListToBanknoteMap(money);
        for (Banknote banknote : changeList.keySet()) {
            moneyCells.get(banknote).getBanknotes(changeList.get(banknote));
        }
        return money;
    }

    private List<Banknote> calculationBanknoteSet(int sumToGetFromBanknoteCell) {
        List<Banknote> money = new ArrayList<>();
        for (BanknoteCell banknoteCell : moneyCells.values()) {
            int banknoteCount = Math.min(banknoteCell.getBanknote().MaxBanknoteCountLessOrEqToSum(sumToGetFromBanknoteCell), banknoteCell.getBanknoteCount());
            if (banknoteCount > 0) {
                money.addAll(Collections.nCopies(banknoteCount, banknoteCell.getBanknote()));
                sumToGetFromBanknoteCell = sumToGetFromBanknoteCell - banknoteCell.getBanknote().getAmountOfNumberBanknote(banknoteCount);
                if (sumToGetFromBanknoteCell == 0)
                    break;
            }
        }
        if (sumToGetFromBanknoteCell != 0) {
            throw new InsufficientFundsException("Unable to withdraw the specified amount");
        }
        return money;
    }

    /**
     * Accept banknotes of different denominations (each denomination must have its own cell)
     *
     * @param banknotes banknotes of different denominations
     * @throws AcceptingFundsException Banknote acceptance error
     */
    @Override
    public void putMoney(@NonNull List<Banknote> banknotes) {
        Map<Banknote, Integer> banknoteSet = BanknoteListToBanknoteMap(banknotes);
        checkAcceptedBanknotes(banknoteSet);
        for (Banknote banknote : banknoteSet.keySet()) {
            moneyCells.get(banknote).putBanknotes(banknoteSet.get(banknote));
        }
    }

    private void checkAcceptedBanknotes(Map<Banknote, Integer> banknoteSet) {
        Optional<Banknote> unsupportedBanknote = banknoteSet.keySet().stream().filter(b -> !moneyCells.containsKey(b)).findAny();

        if (unsupportedBanknote.isPresent()) {
            throw new AcceptingFundsException("Unable to accept banknote " + unsupportedBanknote.get());
        }
    }

    private Map<Banknote, Integer> BanknoteListToBanknoteMap(List<Banknote> banknotes) {
        Map<Banknote, Integer> banknoteSet = new HashMap<Banknote, Integer>();
        banknoteSet.putAll(banknotes.stream().collect(Collectors.toMap(banknote -> banknote, banknote -> 1, Integer::sum)));
        return banknoteSet;
    }
}
