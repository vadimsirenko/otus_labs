package ru.vasire;

import ru.vasire.listeners.*;
import ru.vasire.proxy.CollectionWatcherProxy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<String> testList = new ArrayList<>();

        testPrintListenerTwoWatchers(testList);

        System.out.println("***************  Test write 10_000 items  *******************");
        testChangeMoreItems(10_000, EventType.ADD, testList, AsyncThreadFileCollectionEventListener.class);
        testChangeMoreItems(10_000, EventType.ADD, testList, AsyncExecutorFileCollectionEventListener.class);
        testChangeMoreItems(10_000, EventType.ADD, testList, AsyncBlockedFileCollectionEventListener.class);

        testChangeMoreItems(10_000, EventType.REMOVE, testList, AsyncThreadFileCollectionEventListener.class);
        testChangeMoreItems(10_000, EventType.REMOVE, testList, AsyncExecutorFileCollectionEventListener.class);
        testChangeMoreItems(10_000, EventType.REMOVE, testList, AsyncBlockedFileCollectionEventListener.class);
    }

    private static void testPrintListenerTwoWatchers(List<String> testList) throws InterruptedException {
        System.out.println("testList = " + Arrays.deepToString(testList.toArray()));

        System.out.println("*************** First variant -  CollectionWatcher  *******************");

        CollectionWatcher<String> collectionWatcher = createCollectionWatcher(testList);

        collectionWatcher.add("four");
        collectionWatcher.remove("two");

        System.out.println("*************** Second variant - CollectionWatcherProxy  *******************");

        Collection<String> testListProxy = createCollectionWatcherProxy(testList);
        testListProxy.remove("one");
        testListProxy.remove("three");
        testListProxy.remove("four");
        testListProxy.add("раз");
        testListProxy.add("два");
        testListProxy.add("три");

        System.out.println("testList = " + Arrays.deepToString(testList.toArray()));

        Thread.sleep(1000);
        testList.clear();
    }

    private static CollectionWatcher<String> createCollectionWatcher(List<String> testList) {
        CollectionWatcher<String> collectionWatcher = new CollectionWatcher<>(testList);
        collectionWatcher.addListner(createAddListener(EventType.ADD, PrintCollectionEventListener.class));
        collectionWatcher.addListner(createAddListener(EventType.REMOVE, PrintCollectionEventListener.class));
        return collectionWatcher;
    }

    private static Collection<String> createCollectionWatcherProxy(List<String> testList) {
        Collection<String> testListProxy = CollectionWatcherProxy.createCollectionClass(
                testList,
                createAddListener(EventType.ADD, PrintCollectionEventListener.class),
                createAddListener(EventType.REMOVE, PrintCollectionEventListener.class));
        return testListProxy;
    }

    private static void testChangeMoreItems(int itemCount, EventType eventType, List<String> testList, Class<?> listenerClass) {
        System.out.println();
        System.out.println("**  ListenerClass = " + listenerClass.getSimpleName() + " **");
        long startDt;
        startDt = System.currentTimeMillis();
        try (CollectionEventListener<String> addAsyncBlockedFileListener = createAddListener(eventType, listenerClass)) {

            var testListProxy = CollectionWatcherProxy.createCollectionClass(testList, addAsyncBlockedFileListener);
            for (int i = 0; i < itemCount; i++) {
                switch (eventType) {
                    case ADD:
                        testListProxy.add(" item" + i);
                    case REMOVE:
                        testListProxy.remove(" item" + i);
                        break;
                    default:
                        testListProxy.add(" item" + i);
                }
            }
            System.out.println(eventType + " " + itemCount + " elements in collection " + (System.currentTimeMillis() - startDt) + " ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(itemCount + " elements were executed by listener for " + (System.currentTimeMillis() - startDt) + " ms");
    }

    private static <T> CollectionEventListener<T> createAddListener(EventType eventType, Class<?> listenerClass) {
        return switch (listenerClass.getSimpleName()) {
            case "AsyncBlockedFileCollectionEventListener" ->
                    new AsyncBlockedFileCollectionEventListener<T>(et -> et == eventType, getFileNameByEventType(eventType));
            case "AsyncExecutorFileCollectionEventListener" ->
                    new AsyncExecutorFileCollectionEventListener<T>(et -> et == eventType, getFileNameByEventType(eventType));
            case "AsyncThreadFileCollectionEventListener" ->
                    new AsyncThreadFileCollectionEventListener<T>(et -> et == eventType, getFileNameByEventType(eventType));
            case "PrintCollectionEventListener" -> new PrintCollectionEventListener<T>(et -> et == eventType);
            default -> new PrintCollectionEventListener<T>(et -> et == eventType);
        };
    }

    private static String getFileNameByEventType(EventType eventType) {
        return switch (eventType) {
            case ADD -> "inserted.txt";
            case REMOVE -> "deleted.txt";
            default -> "inserted.txt";
        };
    }
}
