package ru.vasire.bridge.shape;

import ru.vasire.bridge.color.Color;

public class Circle extends Shape {

    private final double radius;

    public Circle(double radius, Color color) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * (radius * radius);
    }

    @Override
    public double getPerimeter() {
        return Math.PI * 2 * radius;
    }

}
