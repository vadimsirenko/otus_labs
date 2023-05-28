package ru.vasire.tests;

import ru.vasire.annotation.After;
import ru.vasire.annotation.Before;
import ru.vasire.annotation.Test;
import ru.vasire.model.Person;
//import ru.vasire.mytestengine.Assertions;
import static org.assertj.core.api.Assertions.*;

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
        assertThat(personFullToTest.getFirstName()).isEqualTo("Вася");
    }
    @Test
    void checkFullConstructorLastName(){
        assertThat(personFullToTest.getLastName()).isEqualTo("Сидоров");
    }
    @Test
    void checkFullConstructorAge(){
        assertThat(personFullToTest.getAge()).isEqualTo(45);
    }

    @Test
    void checkEmptyConstructorFirstName(){
        assertThat(personEmptyToTest.getFirstName()).isEqualTo("Вася");
    }
    @Test
    void checkEmptyConstructorLastName(){
        assertThat(personEmptyToTest.getLastName()).isEqualTo("Сидоров");
    }
    @Test
    void checkEmptyConstructorAge(){
        assertThat(personEmptyToTest.getAge()).isEqualTo(45);
    }

    @After
    void clearPerson(){
        personFullToTest = null;
        personEmptyToTest = null;
    }
}
