package ru.vasire.model;

import ru.vasire.annotation.Log;

public class Person implements PersonInterface {
    private String firstName;
    private String lastName;
    private int age;

    @Log
    public static String getTest(String t)
    {
        return t;
    }

    @Log
    public static Person changePerson(Person p)
    {
        return p;
    }

    @Log
    public Person() {
    }
    @Log
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Log
    public String getFirstName() {
        return firstName;
    }

    @Log
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Log
    public String getLastName() {
        return lastName;
    }

    @Log
    public void setFullName(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Log
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Log
    public int getAge() {
        return age;
    }

    @Log
    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
