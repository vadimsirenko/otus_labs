package ru.vasire.decorator;

public class OrderOption implements Order {

    private final double price;
    private final String label;
    private final Order order;

    public OrderOption(Order order, String label, double price) {
        this.price = price;
        this.label = label;
        this.order = order;
    }

    @Override
    public double getPrice() {
        return order.getPrice() + price;
    }

    @Override
    public String getLabel() {
        return order.getLabel() + ", " + label;
    }
}
