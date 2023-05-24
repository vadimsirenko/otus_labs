package ru.vasire.mytestengine;

import ru.vasire.annotations.After;
import ru.vasire.annotations.Before;
import ru.vasire.annotations.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static ru.vasire.mytestengine.Colors.*;

/**
 * Class for execute tests with annotations After, Before and Test
 */
public class TestEngine {


    /**
     *
     * Execute tests with annotations After, Before and Test classes list
     * @param classes - list name of test class? , for example doTests("ru.vasire.tests.PersonTestConstructors", "NotFoundClass")
     * @throws ReflectiveOperationException exception at Reflection
     */
    public static void doTests(String... classes) throws ReflectiveOperationException {
        TestStatistic statistic = new TestStatistic();

        if(classes.length !=0)
            System.out.println("******************************************");


        for (String classname: classes) {
            Class<?> clazz = getClazz(classname);
            if(clazz == null){
                System.out.println("******************************************");
                continue;
            }
            Method[] methods = getMethods(clazz);
            if(methods.length == 0){
                System.out.println("There is not methods in class " + classname);
                System.out.println("******************************************");
                continue;
            }
            System.out.println("Begin tests from class " + classname);
            executeTestsMethods(methods, statistic);
            System.out.println("******************************************");
        }

        System.out.println(statistic);

    }

    private static Class<?> getClazz(String classname) {
        try {
            return Class.forName(classname);
        }catch (ClassNotFoundException e){
            printStackTrace(e);
            return null;
        }
    }

    private static void executeAfterMethods(Method[] methods, Object instance) throws InvocationTargetException, IllegalAccessException {
        System.out.println("---- after methods ------");
        for (Method method: methods) {
            if(method.getDeclaredAnnotation(After.class) != null && method.getParameterCount() == 0
                    && !Modifier.isStatic(method.getModifiers())){
                invokeMethod(method, instance);
            }
        }
        System.out.println("-----------------");
    }

    private static void executeBeforeMethods(Method[] methods, Object instance) throws InvocationTargetException, IllegalAccessException {
        System.out.println("---- before methods ------");
        for (Method method: methods) {
            if(method.getDeclaredAnnotation(Before.class) != null && method.getParameterCount() == 0
                    && !Modifier.isStatic(method.getModifiers())){
                invokeMethod(method, instance);
            }
        }
        System.out.println("-----------------");
    }

    private static void executeTestsMethods(Method[] methods, TestStatistic statistic) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        for (Method method: methods) {
            if(method.getDeclaredAnnotation(Test.class) != null && method.getParameterCount() == 0
            && !Modifier.isStatic(method.getModifiers())){
                var instance = method.getDeclaringClass().getDeclaredConstructor().newInstance();
                try {
                    executeBeforeMethods(methods, instance);
                    System.out.print("TEST ");
                    invokeMethod(method, instance);
                    statistic.addPassedTest();
                } catch (InvocationTargetException e) {
                    printStackTrace(e);
                    statistic.addFailedTest();
                } finally {
                    executeAfterMethods(methods, instance);
                }
            }
        }
    }

    private static void printStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        if(e != null){
            e.printStackTrace(pw);
            String sStackTrace = sw.toString(); // stack trace as a string
            printRed(sStackTrace);
        }
    }

    private static void invokeMethod(Method method, Object instance) throws IllegalAccessException, InvocationTargetException {
        System.out.println("- " + method.getName());
        boolean access = method.canAccess(instance);
        method.setAccessible(true);
        try {
            method.invoke(instance);
        }
        finally {
            method.setAccessible(access);
        }
    }

    private static Method[] getMethods(Class<?> clazz) {
        return clazz.getDeclaredMethods();
    }

}
