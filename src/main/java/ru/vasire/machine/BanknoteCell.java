package ru.vasire.machine;

public class BanknoteCell extends BanknoteBundle {
    public BanknoteCell(Banknote banknote, int banknoteCount) {
        super(banknote, banknoteCount);
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
}
