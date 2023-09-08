package ru.vasire.listeners;

import ru.vasire.CollectionEvent;

public interface CollectionEventListener<T> extends AutoCloseable {
    void actionPerformed(CollectionEvent<T> event);

    void stopListening();
}
