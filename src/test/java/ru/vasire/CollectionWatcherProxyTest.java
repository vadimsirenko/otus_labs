package ru.vasire;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.vasire.listeners.CollectionEventListener;
import ru.vasire.proxy.CollectionWatcherProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

class CollectionWatcherProxyTest {
    private static Collection<String> collectionWatcherProxy;

    private static ArgumentCaptor<CollectionEvent<String>> collectionEventCaptor;
    private static List<String> testList;

    @BeforeAll
    public static void initListenerCollectionSettings() {
        collectionEventCaptor = ArgumentCaptor.forClass(CollectionEvent.class);
        testList = new ArrayList<>();

        CollectionEventListener<String> addEventListener = mock(CollectionEventListener.class);
        doNothing().when(addEventListener).actionPerformed(collectionEventCaptor.capture());

        collectionWatcherProxy = CollectionWatcherProxy.createCollectionClass(testList, addEventListener);
    }

    @BeforeEach
    public void initCollection() {
        testList.clear();
        testList.addAll(Arrays.asList("one", "two", "three"));
    }

    @Test
    @DisplayName("Checking the addition of a single element")
    void addOneItemTest() {
        collectionWatcherProxy.add("four");
        assertEquals(collectionEventCaptor.getValue().getEventType(), EventType.ADD);
        assertEquals(collectionEventCaptor.getValue().getValue(), "four");
        assertArrayEquals(collectionWatcherProxy.toArray(), new String[]{"one", "two", "three", "four"});
    }

    @Test
    @DisplayName("Checking the deletion of a single element")
    void removeOneItemTest() {
        collectionWatcherProxy.remove("two");
        assertEquals(collectionEventCaptor.getValue().getEventType(), EventType.REMOVE);
        assertEquals(collectionEventCaptor.getValue().getValue(), "two");
        assertArrayEquals(collectionWatcherProxy.toArray(), new String[]{"one", "three"});
    }
}