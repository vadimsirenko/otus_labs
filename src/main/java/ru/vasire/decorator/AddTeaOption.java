package ru.vasire.decorator;

public class AddTeaOption extends OrderOption {
    public AddTeaOption(Order order) {
        super(order, "чай", 80);
    }
}
