package ru.vasire.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BanknoteBundleUtils {
    public static boolean isDuplicateExists(List<? extends BanknoteBundle> banknoteCellList) {
        Set<Banknote> set = new HashSet<>();
        for (BanknoteBundle cell : banknoteCellList) {
            if (set.contains(cell.getBanknote())) {
                return true;
            } else {
                set.add(cell.getBanknote());
            }
        }
        return false;
    }

    public static Optional<List<Banknote>> calculationBanknoteSet(int sumToGetFromBanknoteCell, List<? extends BanknoteBundle> sourceBanknoteList, Comparator<BanknoteBundle> banknoteCellComparator) {
        List<Banknote> money = new ArrayList<>();
        sourceBanknoteList.sort(banknoteCellComparator);
        for (BanknoteBundle banknoteCell : sourceBanknoteList) {
            int banknoteCount = Math.min(banknoteCell.getBanknote().MaxBanknoteCountLessOrEqToSum(sumToGetFromBanknoteCell), banknoteCell.getBanknoteCount());
            if (banknoteCount > 0) {
                money.addAll(Collections.nCopies(banknoteCount, banknoteCell.getBanknote()));
                sumToGetFromBanknoteCell = sumToGetFromBanknoteCell - banknoteCell.getBanknote().getAmountOfNumberBanknote(banknoteCount);

            }
            if (sumToGetFromBanknoteCell == 0)
                break;
        }
        if (sumToGetFromBanknoteCell != 0) {
            return Optional.empty();
        }
        return Optional.of(money);
    }

    public static boolean checkBanknoteBundleListContaintInOtherBanknoteBundleList(List<? extends BanknoteBundle> checkedBanknoteSet, List<? extends BanknoteBundle> targetBanknoteSet) {
        for (BanknoteBundle checkedBanknote : checkedBanknoteSet) {
            if (targetBanknoteSet.stream().noneMatch(mc -> mc.getBanknote() == checkedBanknote.getBanknote())) {
                return false;
            }
        }
        return true;
    }

    public static List<BanknoteBundle> banknoteListToBanknoteBundleList(List<Banknote> banknotes) {
        List<BanknoteBundle> banknoteSet = new ArrayList<>();
        for (Banknote banknote : banknotes) {
            BanknoteBundle banknoteBundle;
            if (banknoteSet.stream().anyMatch(mc -> mc.getBanknote() == banknote)) {
                banknoteBundle = banknoteSet.stream().filter(mc -> mc.getBanknote() == banknote).findAny().get();
                banknoteBundle.setBanknoteCount(banknoteBundle.getBanknoteCount() + 1);
            } else {
                banknoteBundle = new BanknoteBundle(banknote, 1);
                banknoteSet.add(banknoteBundle);
            }
        }
        return banknoteSet;
    }
}



