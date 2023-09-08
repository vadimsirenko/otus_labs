package ru.vasire.proxy;

import ru.vasire.CollectionEvent;
import ru.vasire.EventType;
import ru.vasire.listeners.CollectionEventListener;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionWatcherProxy {

    private CollectionWatcherProxy() {
    }

    public static <T> Collection<T> createCollectionClass(Collection<T> collection, CollectionEventListener<T>... listners) {
        InvocationHandler handler = new CollectionInvocationHandler<>(collection, Arrays.stream(listners).toList());
        return (Collection<T>) Proxy.newProxyInstance(CollectionWatcherProxy.class.getClassLoader(),
                new Class<?>[]{Collection.class}, handler);
    }

    static class CollectionInvocationHandler<T> implements InvocationHandler {
        private final Collection<T> collectionClass;
        private final List<CollectionEventListener<T>> listners;

        CollectionInvocationHandler(Collection<T> collectionClass, List<CollectionEventListener<T>> listners) {
            this.collectionClass = collectionClass;
            this.listners = listners;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Method methodOfInstance = collectionClass.getClass().getDeclaredMethod(method.getName(), method.getParameterTypes());

            switch (methodOfInstance.getName()) {
                case "add" ->
                        listners.forEach(listner -> listner.actionPerformed(new CollectionEvent<>(EventType.ADD, (T) args[0])));
                case "remove" ->
                        listners.forEach(listner -> listner.actionPerformed(new CollectionEvent<>(EventType.REMOVE, (T) args[0])));
            }
            return method.invoke(collectionClass, args);
        }
    }
}
