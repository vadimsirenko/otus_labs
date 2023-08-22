package ru.vasire.machine;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


public class CashMachineSimple implements CashMachine {
    private final SortedSet<BanknoteCell> moneyCells;

    /**
     * Simplified ATM
     *
     * @param banknoteCells Cells with banknotes. Cell values must be unique.
     */
    public CashMachineSimple(@NonNull Set<BanknoteCell> banknoteCells) {
        moneyCells = new TreeSet<>();//new BanknoteBundleNominalDescComparator());
        moneyCells.addAll(banknoteCells);
    }

    /**
     * Returns the balance of funds
     *
     * @return balance of funds
     */
    @Override
    public int getBalance() {
        return moneyCells.stream().mapToInt(BanknoteCell::getBalance).sum();
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
        List<Banknote> money = new ArrayList<>();
        Map<Banknote, Integer> moneyToRemoveFromATM = new HashMap<>();
        int banknoteCount;
        for(BanknoteCell bc: moneyCells){
            banknoteCount = bc.getMoneyCount(requereSum);
            money.addAll(Collections.nCopies(banknoteCount, bc.getBanknote()));
            moneyToRemoveFromATM.put(bc.getBanknote(), banknoteCount);
            requereSum = requereSum - bc.getBanknote().getAmountOfNumberBanknote(banknoteCount);
        }
        if(requereSum == 0){
            moneyCells.forEach(banknoteCell -> {
                if(moneyToRemoveFromATM.get(banknoteCell.getBanknote()) != 0){
                    banknoteCell.removeBanknotes(moneyToRemoveFromATM.get(banknoteCell.getBanknote()));
                }
            });
            return money;
        }
        throw new InsufficientFundsException("Unable to withdraw the specified amount");
    }

    /**
     * Accept banknotes of different denominations (each denomination must have its own cell)
     *
     * @param banknotes banknotes of different denominations
     * @throws AcceptingFundsException Banknote acceptance error
     */
    @Override
    public void putMoney(@NonNull List<Banknote> banknotes) {
        if(banknotes.stream().anyMatch(b -> moneyCells.stream().noneMatch(bc -> bc.getBanknote().equals(b)))){
            throw new AcceptingFundsException("Unable to accept banknote");
        }
        moneyCells.forEach(bc-> bc.addBanknotes((int) banknotes.stream().filter(b->b.equals(bc.getBanknote())).count()) );
    }
}
