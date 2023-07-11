package ru.vasire.machine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BanknoteComparatorTest {
    private List<Banknote> banknotes;

    @BeforeEach
    void init() {
        banknotes = new ArrayList<>();
        banknotes.add(Banknote.N100);
        banknotes.add(Banknote.N5000);
        banknotes.add(Banknote.N500);
        banknotes.add(Banknote.N1000);
    }

    @Test
    void checkSortAsc() {
        banknotes.sort(null);
        assertEquals(banknotes.get(0), Banknote.N100);
        assertEquals(banknotes.get(1), Banknote.N500);
        assertEquals(banknotes.get(2), Banknote.N1000);
        assertEquals(banknotes.get(3), Banknote.N5000);
    }

    @Test
    void checkSortDesc() {
        banknotes.sort(Collections.reverseOrder());
        assertEquals(banknotes.get(3), Banknote.N100);
        assertEquals(banknotes.get(2), Banknote.N500);
        assertEquals(banknotes.get(1), Banknote.N1000);
        assertEquals(banknotes.get(0), Banknote.N5000);
    }
}
