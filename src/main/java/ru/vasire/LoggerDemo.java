package ru.vasire;

import ru.vasire.model.Person;

/*
java -javaagent:loggerDemo.jar -jar loggerDemo.jar
*/

public class LoggerDemo {

    public static void main(String[] args) {
        Person person = new Person();
        person.setFullName("Ivan", "Ivanov");
        person.setAge(25);
        Person person2 = new Person("Vadim", "Sirenko", 49);

        System.out.println(person);
        System.out.println(person2);

        Person.changePerson(person2);

    }

}
