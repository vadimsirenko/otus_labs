package ru.vasire.listeners;

import ru.vasire.CollectionEvent;
import ru.vasire.EventType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class AsyncThreadFileCollectionEventListener<T> extends BaseCollectionEventListener<T> implements AutoCloseable {
    private final LinkedBlockingDeque<CollectionEvent<T>> queue;
    private final Thread writeThread;
    private boolean stoped = false;

    public AsyncThreadFileCollectionEventListener(Function<EventType, Boolean> filter, String filePath) {
        super(filter);
        this.queue = new LinkedBlockingDeque<>();
        this.writeThread = new Thread(() -> {
            writeEventsToFile(filePath);
        });
        writeThread.start();
    }

    private void writeEventsToFile(String filePath) {
        CollectionEvent<T> event;
        do {
            try {
                event = queue.pollFirst(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (event != null) {
                writeEventToFile(event, filePath);
            }
        } while (!stoped || (event != null));
    }

    private void writeEventToFile(CollectionEvent<T> event, String filePath) {
        try {
            Files.write(Paths.get(filePath), (getEventLine(event)).getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
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
