package ru.vasire;

import ru.vasire.listeners.CollectionEventListener;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CollectionWatcher<T> implements Collection<T> {

    private final Collection<T> observableCollection;
    private final List<CollectionEventListener<T>> listners;


    public CollectionWatcher(Collection<T> observableCollection) {
        this.observableCollection = observableCollection;
        this.listners = new ArrayList<>();
    }

    public void addListner(CollectionEventListener<T> listner) {
        listners.add(listner);
    }

    @Override
    public int size() {
        return observableCollection.size();
    }

    @Override
    public boolean isEmpty() {
        return observableCollection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return observableCollection.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return observableCollection.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        observableCollection.forEach(action);
    }

    @Override
    public Object[] toArray() {
        return observableCollection.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return observableCollection.toArray(a);
    }

    @Override
    public <T1> T1[] toArray(IntFunction<T1[]> generator) {
        return observableCollection.toArray(generator);
    }

    @Override
    public boolean add(T t) {
        listners.forEach(listner -> listner.actionPerformed(new CollectionEvent<>(EventType.ADD, t)));
        return observableCollection.add(t);
    }

    @Override
    public boolean remove(Object o) {
        listners.forEach(listner -> listner.actionPerformed(new CollectionEvent<>(EventType.REMOVE, (T) o)));
        return observableCollection.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return observableCollection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return observableCollection.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return observableCollection.removeAll(c);
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        return observableCollection.removeIf(filter);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return observableCollection.retainAll(c);
    }

    @Override
    public void clear() {
        observableCollection.clear();
    }

    @Override
    public boolean equals(Object o) {
        return observableCollection.equals(o);
    }

    @Override
    public int hashCode() {
        return observableCollection.hashCode();
    }

    @Override
    public Spliterator<T> spliterator() {
        return observableCollection.spliterator();
    }

    @Override
    public Stream<T> stream() {
        return observableCollection.stream();
    }

    @Override
    public Stream<T> parallelStream() {
        return observableCollection.parallelStream();
    }
}
