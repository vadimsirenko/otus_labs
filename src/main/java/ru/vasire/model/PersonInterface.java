package ru.vasire.model;

public interface PersonInterface {

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    void setFullName(String firstName, String lastname);

    int getAge();

    void setAge(int age);

    String toString();
}
