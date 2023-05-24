package ru.vasire.tests;

import ru.vasire.annotations.After;
import ru.vasire.annotations.Before;
import ru.vasire.annotations.Test;
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
