package ru.vasire;

import ru.vasire.model.PersonInterface;
import ru.vasire.proxy.Ioc;

public class ProxyDemo {
    public static void main(String[] args) {
        PersonInterface myPerson = Ioc.createPersonClass();
        myPerson.setFullName("Vadim", "Sirenko");
        myPerson.setFirstName("Ivan");
        myPerson.setLastName("Ivanov");
        myPerson.setAge(49);
    }
}
