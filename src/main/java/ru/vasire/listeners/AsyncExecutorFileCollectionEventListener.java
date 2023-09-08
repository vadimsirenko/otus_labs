package ru.vasire.listeners;

import ru.vasire.CollectionEvent;
import ru.vasire.EventType;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

public class AsyncExecutorFileCollectionEventListener<T> extends BaseCollectionEventListener<T> implements AutoCloseable {
    private final LinkedBlockingDeque<CollectionEvent<T>> queue;
    private final List<Future<?>> futureList;
    private final ExecutorService executorService;
    private String filePath;
    private boolean stoped = false;

    public AsyncExecutorFileCollectionEventListener(Function<EventType, Boolean> filter, String filePath) {
        super(filter);
        this.filePath = filePath;
        this.queue = new LinkedBlockingDeque<>();
        this.futureList = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    private void writeEventsToFile() {
        try {
            CollectionEvent<T> event = queue.pollFirst(100, TimeUnit.MILLISECONDS);
            Files.write(Paths.get(filePath), (getEventLine(event)).getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stopListening() {
        if (!stoped) {
            stoped = true;
            for (var future : futureList) {
                try {
                    future.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
            executorService.shutdown();
        }
    }

    @Override
    public void doWork(CollectionEvent<T> event) {
        queue.add(event);
        futureList.add(executorService.submit(this::writeEventsToFile));
    }

    @Override
    public void close() throws Exception {
        stopListening();
    }
}
