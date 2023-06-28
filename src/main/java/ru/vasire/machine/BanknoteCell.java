package ru.vasire.machine;

import java.util.Collections;
import java.util.List;

public class BanknoteCell {

    private final Banknote banknote;

    private int banknoteCount;

    /**
     * A cell with banknotes of the same denomination
     *
     * @param banknote      denomination of banknotes
     * @param banknoteCount The number of banknotes
     */
    public BanknoteCell(Banknote banknote, int banknoteCount) {
        this.banknote = banknote;
        if (banknoteCount < 0) {
            throw new IllegalArgumentException("The number of banknotes cannot be negative");
        }
        this.banknoteCount = banknoteCount;
    }

    public Banknote getBanknote() {
        return banknote;
    }

    public int getBanknoteCount() {
        return banknoteCount;
    }

    public int getBalance() {
        return banknote.getAmountOfNumberBanknote(banknoteCount);
    }

    /**
     * Adding several banknotes to a cell
     *
     * @param num number of banknotes
     */
    public void putBanknotes(int num) {
        banknoteCount = banknoteCount + num;
    }

    /**
     * Adding several banknotes to a banknote cell
     *
     * @param num number of banknotes
     */
    public List<Banknote> getBanknotes(int num) {
        if (num > banknoteCount) {
            throw new InsufficientFundsException("The requested number of banknotes is greater than the balance");
        }
        banknoteCount = banknoteCount - num;
        return Collections.nCopies(num, banknote);
    }

}
