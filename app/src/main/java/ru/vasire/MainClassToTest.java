package ru.vasire;

import ru.vasire.mytestengine.TestEngine;
import ru.vasire.tests.PersonTestConstructors;
import ru.vasire.tests.PersonTestFields;

/**
 *  Class for test engine TestEngine
 */
public class MainClassToTest {

    public static void main(String[] args)  {
        try {


            TestEngine.doTests(PersonTestConstructors.class.getName(),
                    "NotFoundClass",
                    PersonTestFields.class.getName());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}
