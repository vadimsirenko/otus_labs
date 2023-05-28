package ru.vasire.tests;

import ru.vasire.annotation.After;
import ru.vasire.annotation.Before;
import ru.vasire.annotation.Test;
import ru.vasire.model.Person;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonTestFields {
    Person personToTest;

    @Before
    void initPerson(){
        personToTest = new Person();
    }

    @Test
    void checkSetFirstName(){
        personToTest.setFirstName("Вася");
        assertThat(personToTest.getFirstName()).isEqualTo("Вася");
    }
    @Test
    void checkSetLastName(){
        personToTest.setLastName("Сидоров");
        assertThat(personToTest.getLastName()).isEqualTo("Сидоров");
    }
    @Test
    void checkSetAge(){
        personToTest.setAge(45);
        assertThat(personToTest.getAge()).isEqualTo(45);
    }

    @After
    void clearPerson(){
        personToTest = null;
    }
}
