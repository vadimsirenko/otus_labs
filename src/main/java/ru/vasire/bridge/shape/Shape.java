package ru.vasire.bridge.shape;

import ru.vasire.bridge.color.Color;

public abstract class Shape {
    public Color color;

    public Shape(Color color) {
        this.color = color;
    }

    public abstract double getArea();

    public abstract double getPerimeter();
}
