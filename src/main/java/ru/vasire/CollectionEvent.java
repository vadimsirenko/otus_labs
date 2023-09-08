package ru.vasire;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CollectionEvent<T> {
    private EventType eventType;
    private T value;
}
