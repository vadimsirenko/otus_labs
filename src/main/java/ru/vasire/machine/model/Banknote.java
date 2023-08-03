package ru.vasire.machine.model;

import java.util.List;

public enum Banknote implements Comparable<Banknote> {
    N100(100),
    N500(500),
    N1000(1000),
    N5000(5000);
    /**
     * Banknote denomination
     */
    private final int denomination;

    /**
     * Banknote
     *
     * @param denomination Banknote denomination
     */
    Banknote(int denomination) {
        this.denomination = denomination;
    }

    /**
     * Returns the amount of banknotes
     *
     * @param banknoteList banknote set
     * @return amount of banknotes
     */
    public static int getAmountOfBanknotes(List<Banknote> banknoteList) {
        return banknoteList.stream().mapToInt(b -> b.denomination).sum();
    }

    /**
     * Returns the monetary amount of the specified number of banknotes of the current denomination
     *
     * @param banknoteNum number of banknotes
     * @return amount of banknotes
     */
    public int getAmountOfNumberBanknote(int banknoteNum) {
        return this.denomination * banknoteNum;
    }

    /**
     * Returns the closest number of banknotes of the current denomination, but not more than the specified amount
     *
     * @param sum requested amount
     * @return number of banknotes
     */
    public int MaxBanknoteCountLessOrEqToSum(int sum) {
        return sum / this.denomination;
    }
}
