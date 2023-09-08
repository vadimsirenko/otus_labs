package ru.vasire;

import java.util.concurrent.locks.Lock;

public class SequenceTask implements Runnable {
    private final int taskNumber;
    SequenceConditions sequenceConditions;
    Lock lock;

    public SequenceTask(int taskNumber, Lock lock, SequenceConditions sequenceConditions) {
        this.taskNumber = taskNumber;
        this.sequenceConditions = sequenceConditions;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            for (int n = 1; n <= 10; n++) {
                writeNumber(n);
            }
            for (int n = 9; n > 0; n--) {
                writeNumber(n);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeNumber(int n) throws InterruptedException {
        lock.lock();
        try {
            sequenceConditions.awaitAndThenSignal(taskNumber);
            System.out.println(n + " " + Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }
}