package ru.vasire.machine;

import lombok.NonNull;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class CashMachineSimple implements CashMachine {
    private final List<BanknoteCell> moneyCells;

    /**
     * Simplified ATM
     *
     * @param banknoteCells Cells with banknotes. Cell values must be unique.
     */
    public CashMachineSimple(@NonNull List<BanknoteCell> banknoteCells) {

        if (BanknoteBundleUtils.isDuplicateExists(banknoteCells)) {
            throw new IllegalArgumentException("There can be only one cell with each denomination");
        }
        moneyCells = banknoteCells;
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
        return getMoney(requereSum, new BanknoteBundleNominalDescComparator());
    }

    public List<Banknote> getMoney(int requereSum, Comparator<BanknoteBundle> banknoteCellComparator) {
        if (requereSum <= 0) {
            throw new InsufficientFundsException("Requested amount must be greater than zero");
        }
        Optional<List<Banknote>> moneyOptional = BanknoteBundleUtils.calculationBanknoteSet(requereSum, moneyCells, banknoteCellComparator);
        if (moneyOptional.isEmpty()) {
            throw new InsufficientFundsException("Unable to withdraw the specified amount");
        }
        List<BanknoteBundle> changeList = BanknoteBundleUtils.banknoteListToBanknoteBundleList(moneyOptional.get());
        for (BanknoteBundle banknoteBundle : changeList) {
            BanknoteCell banknoteCell = moneyCells.stream().filter(mc -> mc.getBanknote() == banknoteBundle.getBanknote()).findFirst().orElse(null);
            assert banknoteCell != null;
            banknoteCell.removeBanknotes(banknoteBundle.getBanknoteCount());
        }
        return moneyOptional.get();
    }

    /**
     * Accept banknotes of different denominations (each denomination must have its own cell)
     *
     * @param banknotes banknotes of different denominations
     * @throws AcceptingFundsException Banknote acceptance error
     */
    @Override
    public void putMoney(@NonNull List<Banknote> banknotes) {
        List<BanknoteBundle> banknoteSet = BanknoteBundleUtils.banknoteListToBanknoteBundleList(banknotes);
        if (!BanknoteBundleUtils.checkBanknoteBundleListContaintInOtherBanknoteBundleList(banknoteSet, moneyCells)) {
            throw new AcceptingFundsException("Unable to accept banknote");
        }
        for (BanknoteBundle banknoteBundle : banknoteSet) {
            BanknoteCell banknoteCell = moneyCells.stream().filter(mc -> mc.getBanknote() == banknoteBundle.getBanknote()).findFirst().orElse(null);
            assert banknoteCell != null;
            banknoteCell.addBanknotes(banknoteBundle.getBanknoteCount());
        }
    }
}
