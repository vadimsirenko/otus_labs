package ru.vasire.tests;

import ru.vasire.annotation.After;
import ru.vasire.annotation.Before;
import ru.vasire.annotation.Test;
import ru.vasire.model.Person;
import ru.vasire.mytestengine.Assertions;

public class PersonTestFields {
    Person personToTest;

    @Before
    void initPerson(){
        personToTest = new Person();
    }

    @Test
    void checkSetFirstName(){
        personToTest.setFirstName("Вася");
        Assertions.isTrue(personToTest.getFirstName().equals("Вася"),"Поле firstName сохраняется");
    }
    @Test
    void checkSetLastName(){
        personToTest.setLastName("Сидоров");
        Assertions.isTrue(personToTest.getLastName().equals("Сидоров"),"Поле lastName сохраняется");
    }
    @Test
    void checkSetAge(){
        personToTest.setAge(45);
        Assertions.isTrue(personToTest.getAge() == 45,"Поле age сохраняется");
    }

    @After
    void clearPerson(){
        personToTest = null;
    }
}
