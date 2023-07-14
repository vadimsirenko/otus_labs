package ru.vasire.report;

import java.io.Serializable;
import java.util.Arrays;
import java.util.TreeSet;

public class BelongNumberMessages extends TreeSet<BelongNumberMessage> implements Serializable {
    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }
}
