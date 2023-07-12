package ru.vasire;

import ru.vasire.decorator.*;

public class DecoratorToTest {

    public static void main(String[] args) {
        Order order = new OrderImpl("Сосиска", 30.0);
        order = new AddSouceOption(order);
        order = new DoublePortionOption(order);
        order = new AddTeaOption(order);

        System.out.println(order.getLabel() + "  " + order.getPrice());


        Order order2 = new OrderImpl("Сосиска", 30.0);
        order2 = new DoublePortionOption(order2);
        order2 = new AddSouceOption(order2);
        order2 = new AddTeaOption(order2);

        System.out.println(order2.getLabel() + "  " + order2.getPrice());

    }

}
