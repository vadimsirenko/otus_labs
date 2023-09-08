package ru.vasire;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.vasire.listeners.CollectionEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class CollectionWatcherTest {

    private static CollectionWatcher<String> collectionWatcher;

    private static ArgumentCaptor<CollectionEvent<String>> collectionEventCaptor;
    private static List<String> testList;

    @BeforeAll
    public static void initListenerCollectionSettings() {
        collectionEventCaptor = ArgumentCaptor.forClass(CollectionEvent.class);
        testList = new ArrayList<>();

        CollectionEventListener<String> addEventListener = mock(CollectionEventListener.class);
        doNothing().when(addEventListener).actionPerformed(collectionEventCaptor.capture());

        collectionWatcher = new CollectionWatcher<>(testList);
        collectionWatcher.addListner(addEventListener);
    }

    @BeforeEach
    public void initCollection() {
        testList.clear();
        testList.addAll(Arrays.asList("one", "two", "three"));
    }

    @Test
    @DisplayName("Checking the addition of a single element")
    void addOneItemTest() {
        collectionWatcher.add("four");
        assertEquals(collectionEventCaptor.getValue().getEventType(), EventType.ADD);
        assertEquals(collectionEventCaptor.getValue().getValue(), "four");
        assertArrayEquals(collectionWatcher.toArray(), new String[]{"one", "two", "three", "four"});
    }

    @Test
    @DisplayName("Checking the deletion of a single element")
    void removeOneItemTest() {
        collectionWatcher.remove("two");
        assertEquals(collectionEventCaptor.getValue().getEventType(), EventType.REMOVE);
        assertEquals(collectionEventCaptor.getValue().getValue(), "two");
        assertArrayEquals(collectionWatcher.toArray(), new String[]{"one", "three"});
    }
}