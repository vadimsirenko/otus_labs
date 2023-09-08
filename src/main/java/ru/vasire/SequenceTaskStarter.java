package ru.vasire;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SequenceTaskStarter {
    public static void startThread(int threadCount) {

        Lock lock = new ReentrantLock();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        SequenceConditions sequenceConditions = new SequenceConditions(threadCount, lock);

        for (int i = 0; i < threadCount; i++) {

            executorService.execute(new SequenceTask(i, lock, sequenceConditions));
        }
        executorService.shutdown();
    }
}
