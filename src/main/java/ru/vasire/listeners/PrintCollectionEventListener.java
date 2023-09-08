package ru.vasire.listeners;

import ru.vasire.CollectionEvent;
import ru.vasire.EventType;

import java.util.function.Function;

public class PrintCollectionEventListener<T> extends BaseCollectionEventListener<T> {
    public PrintCollectionEventListener(Function<EventType, Boolean> filter) {
        super(filter);
    }

    @Override
    public void doWork(CollectionEvent<T> event) {
        System.out.println(event.getEventType() + " item = " + event.getValue());
    }
}
