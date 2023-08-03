package ru.vasire.machine.model;

public class BanknoteBundle {

    private final Banknote banknote;

    private int banknoteCount;

    /**
     * A cell with banknotes of the same denomination
     *
     * @param banknote      denomination of banknotes
     * @param banknoteCount The number of banknotes
     */
    public BanknoteBundle(Banknote banknote, int banknoteCount) {
        if (banknoteCount < 0) {
            throw new IllegalArgumentException("The number of banknotes cannot be negative");
        }
        this.banknote = banknote;
        this.banknoteCount = banknoteCount;
    }

    public Banknote getBanknote() {
        return banknote;
    }

    public int getBanknoteCount() {
        return banknoteCount;
    }

    protected void setBanknoteCount(int banknoteCount) {
        this.banknoteCount = banknoteCount;
    }

    public int getBalance() {
        return banknote.getAmountOfNumberBanknote(banknoteCount);
    }
}
