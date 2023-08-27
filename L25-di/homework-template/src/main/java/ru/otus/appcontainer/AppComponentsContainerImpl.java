package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?>... configClasses) {

        List<BeanDescription> beanDescriptionList = new ArrayList<>();

        for (Class<?> configClass : configClasses) {
            checkConfigClass(configClass);
            // You code here...
            Object configClassInstanseInstanse = getConfigClassInstanse(configClass);

            getBeanDescriptions(beanDescriptionList, configClass, configClassInstanseInstanse);
        }
        reorderBuildBeanDescriptionList(beanDescriptionList);

        collectCongigurationLists(beanDescriptionList);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
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
        int configOrder = configClass.getAnnotation(AppComponentsContainerConfig.class).order();
        for (Method method : configClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                var annotation = method.getAnnotation(AppComponent.class);
                if (beanDescriptionList.stream().anyMatch(bd -> bd.name.equals(annotation.name()))) {
                    throw new RuntimeException(String.format("Duplicate of the Bean name %s", annotation.name()));
                }
                beanDescriptionList.add(new BeanDescription(configOrder * 10_000 + annotation.order(), annotation.name(), method, configClassInstanseInstanse));
            }
        }
        return beanDescriptionList;
    }

    private void reorderBuildBeanDescriptionList(List<BeanDescription> beanDescriptionList) {
        Collections.sort(beanDescriptionList);
    }

    private void collectCongigurationLists(List<BeanDescription> beanDescriptionList) {
        for (BeanDescription beanDescription : beanDescriptionList) {
            Object component = null;
            try {
                var params = Arrays.stream(beanDescription.method.getParameterTypes()).map(pt -> getAppComponent(pt)).toArray();
                component = beanDescription.method.invoke(beanDescription.configObject, params);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            appComponents.add(component);
            appComponentsByName.put(beanDescription.name, component);
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<C> cmpList = (List<C>) appComponents.stream().filter(c -> componentClass.isInstance(c)).toList();
        if (cmpList.size() > 1) {
            throw new RuntimeException(String.format("Duplicate Bean component %s", componentClass.getName()));
        }
        if (cmpList.size() == 0) {
            throw new RuntimeException(String.format("Bean %s is not registered", componentClass.getName()));
        }
        return cmpList.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        C cmp = (C) appComponentsByName.get(componentName);
        if (cmp == null) {
            throw new RuntimeException(String.format("Bean %s is not registered", componentName));
        }
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

