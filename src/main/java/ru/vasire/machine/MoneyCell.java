package ru.vasire.machine;

import java.util.Collections;
import java.util.List;

/**
 * A cell with banknotes of the same denomination
 */
public class MoneyCell {
    private final Banknote banknote;
    private int banknoteCount;

    /**
     * A cell with banknotes of the same denomination
     *
     * @param banknote      denomination of banknotes
     * @param banknoteCount The number of banknotes
     */
    public MoneyCell(Banknote banknote, int banknoteCount) {
        this.banknote = banknote;
        if (banknoteCount < 0) {
            throw new IllegalArgumentException("The number of banknotes cannot be negative");
        }
        this.banknoteCount = banknoteCount;
    }

    protected int getBalance() {
        return banknote.getSumOfNumberBanknote(banknoteCount);
    }

    /**
     * Returns the denomination of the banknotes in the cell
     *
     * @return Denomination of banknotes
     */
    public Banknote getBanknote() {
        return banknote;
    }

    /**
     * Returns the number of banknotes corresponding to the requested amount
     * or less depending on the face value or the entire free balance.
     *
     * @param sum requested amount
     * @return Banknotes
     */
    protected List<Banknote> getPossible(int sum) {
        int returnBanknoteCount = Math.min(banknote.MaxBanknoteCountLessOrEqToSum(sum), banknoteCount);
        this.banknoteCount = this.banknoteCount - returnBanknoteCount;
        return Collections.nCopies(returnBanknoteCount, this.banknote);
    }

    /**
     * Adding one banknote to a cell
     */
    protected void putOne() {
        putBanknotes(1);
    }

    /**
     * Adding several banknotes to a cell
     *
     * @param num number of banknotes
     */
    protected void putBanknotes(int num) {
        banknoteCount = banknoteCount + num;
    }

}
