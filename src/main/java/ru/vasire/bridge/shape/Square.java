package ru.vasire.bridge.shape;

import ru.vasire.bridge.color.Color;

public class Square extends Shape {

    private final double a;

    public Square(double a, Color color) {
        super(color);
        this.a = a;
    }

    @Override
    public double getArea() {
        return a * a;
    }

    @Override
    public double getPerimeter() {
        return 4 * a;
    }

}