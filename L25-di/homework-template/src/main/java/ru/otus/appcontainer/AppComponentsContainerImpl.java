package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?>... configClasses) {

        List<BeanDescription> beanDescriptionList = new ArrayList<>();

        for (Class<?> configClass: configClasses) {
            checkConfigClass(configClass);
            // You code here...
            Object configClassInstanseInstanse = getConfigClassInstanse(configClass);

            getBeanDescriptions(beanDescriptionList, configClass, configClassInstanseInstanse);
        }
        reorderBuildbeanDescriptionList(beanDescriptionList);

        collectCongigurationLists(beanDescriptionList);
    }

    private void reorderBuildbeanDescriptionList(List<BeanDescription> beanDescriptionList) {
        Collections.sort(beanDescriptionList);
    }

    private void collectCongigurationLists(List<BeanDescription> beanDescriptionList) {
        for (BeanDescription beanDescription: beanDescriptionList) {
            Object component = null;
            try {
                var params = Arrays.stream(beanDescription.method.getParameterTypes()).map(pt->getAppComponent(pt)).toArray();
                component = beanDescription.method.invoke(beanDescription.configObject,  params);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            appComponents.add(component);
            appComponentsByName.put(beanDescription.name, component);
        }
    }

    private static Object getConfigClassInstanse(Class<?> configClass) {
        Object confifInstanse;
        try {
            confifInstanse = configClass.getDeclaredConstructors()[0].newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return confifInstanse;
    }

    private List<BeanDescription> getBeanDescriptions(List<BeanDescription> beanDescriptionList, Class<?> configClass, Object configClassInstanseInstanse) {

        for(Method method: configClass.getDeclaredMethods()){
            if(method.isAnnotationPresent(AppComponent.class)){
                var annotation = method.getAnnotation(AppComponent.class);
                beanDescriptionList.add(new BeanDescription(annotation.order(), annotation.name(), method, configClassInstanseInstanse));
            }
        }
        return beanDescriptionList;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream().filter(c-> componentClass.isInstance(c)).findFirst().orElse(null);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    class BeanDescription implements Comparable<BeanDescription> {
        int order;
        String name;
        Method method;

        Object configObject;

        public BeanDescription(int order, String name, Method method, Object configObject) {
            this.order = order;
            this.name = name;
            this.method = method;
            this.configObject = configObject;
        }

        @Override
        public int compareTo(BeanDescription other) {
            return Integer.compare(order, other.order);
        }
    }

}

