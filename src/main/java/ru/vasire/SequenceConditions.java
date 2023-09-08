package ru.vasire;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SequenceConditions {
    private final List<Condition> conditions;
    private int currentActiveThread = 0;

    public SequenceConditions(int conditionNum, Lock lock) {
        this.conditions = new ArrayList<>(conditionNum);
        for (int i = 0; i < conditionNum; i++) {
            conditions.add(lock.newCondition());
        }
    }

    public void awaitAndThenSignal(int num) throws InterruptedException {
        while (num != currentActiveThread) {
            conditions.get(num).await();
        }
        currentActiveThread = getNextThreadNumber(num);
        conditions.get(currentActiveThread).signal();
    }

    private int getNextThreadNumber(int num) {
        if (num == (conditions.size() - 1)) {
            return 0;
        } else {
            return num + 1;
        }
    }
}
