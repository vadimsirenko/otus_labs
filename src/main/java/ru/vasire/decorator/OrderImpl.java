package ru.vasire.decorator;

public class OrderImpl implements Order {
    private final double price;
    private final String label;

    public OrderImpl(String label, double price) {
        this.label = label;
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getLabel() {
        return label;
    }
}
