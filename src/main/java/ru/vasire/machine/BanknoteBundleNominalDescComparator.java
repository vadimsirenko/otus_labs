package ru.vasire.machine;

import java.util.Comparator;

public class BanknoteBundleNominalDescComparator implements Comparator<BanknoteBundle> {
    @Override
    public int compare(BanknoteBundle bc1, BanknoteBundle bc2) {
        return bc2.getBanknote().compareTo(bc1.getBanknote());
    }
}
