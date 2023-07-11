package ru.vasire.machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BanknoteBundleUtilsTest {
    private List<BanknoteBundle> banknoteBundleOne;
    private List<BanknoteBundle> banknoteBundleAll;

    @BeforeEach
    void setUp() {
        banknoteBundleOne = Arrays.asList(new BanknoteBundle(Banknote.N100, 100), new BanknoteCell(Banknote.N500, 500));
        banknoteBundleAll = Arrays.asList(new BanknoteBundle(Banknote.N100, 100), new BanknoteCell(Banknote.N500, 500),
                new BanknoteBundle(Banknote.N1000, 1000), new BanknoteBundle(Banknote.N5000, 5000));
    }

    @Test
    void isDuplicateExistsTrue() {
        boolean expectedVal = false;
        boolean testVal = BanknoteBundleUtils.isDuplicateExists(banknoteBundleOne);
        assertEquals(testVal, expectedVal);
    }

    @Test
    void isDuplicateExistsFalse() {
        boolean expectedVal = true;
        banknoteBundleOne = Arrays.asList(new BanknoteBundle(Banknote.N100, 100), new BanknoteBundle(Banknote.N100, 100), new BanknoteCell(Banknote.N500, 500));
        boolean testVal = BanknoteBundleUtils.isDuplicateExists(banknoteBundleOne);
        assertEquals(testVal, expectedVal);
    }

    @Test
    void calculationBanknoteSetRight() {
        Optional<List<Banknote>> banknoteBundles = BanknoteBundleUtils.calculationBanknoteSet(5100, banknoteBundleAll, new BanknoteBundleNominalDescComparator());
        assertEquals(true, banknoteBundles.isPresent());
        assertEquals(2, banknoteBundles.get().size());
        assertEquals(true, banknoteBundles.get().contains(Banknote.N100));
        assertEquals(true, banknoteBundles.get().contains(Banknote.N5000));
    }

    @Test
    void calculationBanknoteSetNotNominal() {
        Optional<List<Banknote>> banknoteBundles = BanknoteBundleUtils.calculationBanknoteSet(550, banknoteBundleAll, new BanknoteBundleNominalDescComparator());
        assertEquals(false, banknoteBundles.isPresent());
    }

    @Test
    void checkBanknoteBundleListContaintInOtherBanknoteBundleList() {
        boolean expectedVal = true;
        boolean testVal = BanknoteBundleUtils.checkBanknoteBundleListContaintInOtherBanknoteBundleList(banknoteBundleOne, banknoteBundleAll);
        assertEquals(testVal, expectedVal);
        expectedVal = false;
        testVal = BanknoteBundleUtils.checkBanknoteBundleListContaintInOtherBanknoteBundleList(banknoteBundleAll, banknoteBundleOne);
        assertEquals(testVal, expectedVal);
    }

    @Test
    void banknoteListToBanknoteBundleList() {
        List<Banknote> banknotes = Arrays.asList(Banknote.N5000, Banknote.N5000, Banknote.N100, Banknote.N100, Banknote.N500);
        List<BanknoteBundle> banknoteBundleList = BanknoteBundleUtils.banknoteListToBanknoteBundleList(banknotes);
        banknoteBundleList.sort(new BanknoteBundleNominalDescComparator());
        assertEquals(Banknote.N5000, banknoteBundleList.get(0).getBanknote());
        assertEquals(2, banknoteBundleList.get(0).getBanknoteCount());
        assertEquals(Banknote.N500, banknoteBundleList.get(1).getBanknote());
        assertEquals(1, banknoteBundleList.get(1).getBanknoteCount());
        assertEquals(Banknote.N100, banknoteBundleList.get(2).getBanknote());
        assertEquals(2, banknoteBundleList.get(2).getBanknoteCount());
        assertEquals(3, banknoteBundleList.size());
    }
}