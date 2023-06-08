package ru.vasire.logger;


import ru.vasire.model.Person;

import java.lang.reflect.Method;

public class MainForDebug {
    public static void main(String[] args) throws Exception {
        LoggerClassLoader loggerClassLoader = new LoggerClassLoader();
        Class<?> clazz = loggerClassLoader.findClass("ru.vasire.model.Person");
        Method method = clazz.getDeclaredMethod("setFullName", String.class, String.class);
        Object person = clazz.getDeclaredConstructor().newInstance();
        method.invoke(person, "Vadim", "Sirenko");
    }
}
