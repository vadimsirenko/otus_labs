package ru.vasire;

import ru.vasire.mytestengine.TestEngine;

import java.lang.reflect.InvocationTargetException;

/**
 *  Class for test engine TestEngine
 */
public class MainClassToTest {

    public static void main(String[] args)  {
        try {
            TestEngine.doTests("ru.vasire.tests.PersonTestConstructors", "NotFoundClass",
                    "ru.vasire.tests.PersonTestFields");
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}
