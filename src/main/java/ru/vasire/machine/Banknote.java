package ru.vasire.machine;

import java.util.Arrays;
import java.util.List;

public enum Banknote {
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
     * @param banknotes banknote set
     * @return amount of banknotes
     */
    public static int getBanknoteSum(Banknote... banknotes) {
        return getBanknoteSum(Arrays.stream(banknotes).toList());
    }

    /**
     * Returns the amount of banknotes
     *
     * @param banknoteList banknote set
     * @return amount of banknotes
     */
    public static int getBanknoteSum(List<Banknote> banknoteList) {
        return banknoteList.stream().mapToInt(b -> b.denomination).sum();
    }

    /**
     * Returns the monetary amount of the specified number of banknotes of the current denomination
     *
     * @param banknoteNum number of banknotes
     * @return amount of banknotes
     */
    public int getSumOfNumberBanknote(int banknoteNum) {
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

    /**
     * The function of comparing banknotes by face value
     */
    public static class BanknoteComparator implements java.util.Comparator<Banknote> {
        @Override
        public int compare(Banknote a, Banknote b) {
            return Integer.compare(a.denomination, b.denomination);
        }
    }
}
