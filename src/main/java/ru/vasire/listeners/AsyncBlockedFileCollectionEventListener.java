package ru.vasire.listeners;

import ru.vasire.CollectionEvent;
import ru.vasire.EventType;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class AsyncBlockedFileCollectionEventListener<T> extends BaseCollectionEventListener<T> implements AutoCloseable {
    private final LinkedBlockingDeque<CollectionEvent<T>> queue;
    private final Thread writeThread;
    private boolean stoped = false;

    public AsyncBlockedFileCollectionEventListener(Function<EventType, Boolean> filter, String filePath) {
        super(filter);
        this.queue = new LinkedBlockingDeque<>();
        this.writeThread = new Thread(() -> {
            writeEventsToFile(filePath);
        });
        writeThread.start();
    }

    private void writeEventsToFile(String filePath) {
        CollectionEvent<T> event;
        try (FileWriter fw = new FileWriter(filePath, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            do {
                event = queue.pollFirst(100, TimeUnit.MILLISECONDS);
                if (event != null) {
                    out.print(getEventLine(event));
                }
            } while (!stoped || (event != null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stopListening() {
        if (!stoped) {
            stoped = true;
            try {
                this.writeThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void doWork(CollectionEvent<T> event) {
        queue.add(event);
    }

    @Override
    public void close() throws Exception {
        stopListening();
    }
}
