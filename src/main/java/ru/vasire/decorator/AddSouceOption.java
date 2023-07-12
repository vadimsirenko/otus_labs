package ru.vasire.decorator;

public class AddSouceOption extends OrderOption {
    public AddSouceOption(Order order) {
        super(order, "майонез", 25);
    }
}
