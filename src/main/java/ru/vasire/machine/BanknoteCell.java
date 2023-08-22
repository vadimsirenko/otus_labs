package ru.vasire.machine;

public class BanknoteCell implements Comparable<BanknoteCell> {
    private final Banknote banknote;
    private int banknoteCount;

    /**
     * A cell with banknotes of the same denomination
     *
     * @param banknote      denomination of banknotes
     * @param banknoteCount The number of banknotes
     */
    public BanknoteCell(Banknote banknote, int banknoteCount) {
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

    public void setBanknoteCount(int banknoteCount) {
        this.banknoteCount = banknoteCount;
    }

    public int getBalance() {
        return banknote.getAmountOfNumberBanknote(banknoteCount);
    }
    public void removeBanknotes(int num) {
        if (num > getBanknoteCount()) {
            throw new InsufficientFundsException("The requested number of banknotes is greater than the balance");
        }
        if (num < 0) {
            throw new IllegalArgumentException("The number of banknotes cannot be negative");
        }
        setBanknoteCount(getBanknoteCount() - num);
    }

    public void addBanknotes(int num) {
        setBanknoteCount(getBanknoteCount() + num);
    }

    public int getMoneyCount(int requereSum){
        return Math.min(getBanknote().MaxBanknoteCountLessOrEqToSum(requereSum), getBanknoteCount());
    }

    @Override
    public int hashCode() {
        return this.getBanknote().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BanknoteCell) {
            return this.getBanknote().equals(((BanknoteCell) obj).getBanknote());
        }
        else{
            return false;
        }
    }

    @Override
    public int compareTo(BanknoteCell o) {
        return o.getBanknote().compareTo(this.getBanknote());
    }
}
