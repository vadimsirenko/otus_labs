package ru.vasire.tests;

import ru.vasire.annotations.After;
import ru.vasire.annotations.Before;
import ru.vasire.annotations.Test;
import ru.vasire.model.Person;
import ru.vasire.mytestengine.Assertions;

public class PersonTestConstructors {

    Person personFullToTest;
    Person personEmptyToTest;

    @Before
    void initPersonFull(){
        personFullToTest = new Person("Вася", "Сидоров", 45);
    }

    @Before
    void initPersonEmpty(){
        personEmptyToTest = new Person();
    }

    @Test
    void checkFullConstructorFirstName(){
        Assertions.isTrue(personFullToTest.getFirstName().equals("Вася"),"Поле firstName сохраняется");
    }
    @Test
    void checkFullConstructorLastName(){
        Assertions.isTrue(personFullToTest.getLastName().equals("Сидоров"),"Поле lastName сохраняется");
    }
    @Test
    void checkFullConstructorAge(){
        Assertions.isTrue(personFullToTest.getAge() == 45,"Поле age сохраняется");
    }

    @Test
    void checkEmptyConstructorFirstName(){
        Assertions.isTrue(personEmptyToTest.getFirstName().equals("Вася"),"Поле firstName сохраняется");
    }
    @Test
    void checkEmptyConstructorLastName(){
        Assertions.isTrue(personEmptyToTest.getLastName().equals("Сидоров"),"Поле lastName сохраняется");
    }
    @Test
    void checkEmptyConstructorAge(){
        Assertions.isTrue(personEmptyToTest.getAge() == 45,"Поле age сохраняется");
    }

    @After
    void clearPerson(){
        personFullToTest = null;
        personEmptyToTest = null;
    }
}
