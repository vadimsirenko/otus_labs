package ru.vasire.listeners;

import lombok.RequiredArgsConstructor;
import ru.vasire.CollectionEvent;
import ru.vasire.EventType;

import java.util.function.Function;

@RequiredArgsConstructor
public abstract class BaseCollectionEventListener<T> implements CollectionEventListener<T> {

    private final Function<EventType, Boolean> filter;

    @Override
    public void actionPerformed(CollectionEvent<T> event) {
        if (filter.apply(event.getEventType())) {
            doWork(event);
        }
    }

    @Override
    public void stopListening() {
    }

    @Override
    public void close() throws Exception {
    }

    public abstract void doWork(CollectionEvent<T> event);

    public String getEventLine(CollectionEvent<T> event) {
        return String.format("thread %s, classListener %s, eventType %s, value %s",
                Thread.currentThread().getName(),
                this.getClass().getSimpleName(),
                event.getEventType(),
                event.getValue().toString()) + System.lineSeparator();
    }
}
