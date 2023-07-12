package ru.vasire.decorator;

public class DoublePortionOption implements Order {
    private final Order order;

    public DoublePortionOption(Order order) {
        this.order = order;
    }

    @Override
    public double getPrice() {
        return order.getPrice() * 2;
    }

    @Override
    public String getLabel() {
        return "[" + order.getLabel() + "] двойное";
    }
}
