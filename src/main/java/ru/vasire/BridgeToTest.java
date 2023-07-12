package ru.vasire;

import ru.vasire.bridge.color.BlackColor;
import ru.vasire.bridge.color.RedColor;
import ru.vasire.bridge.shape.Circle;
import ru.vasire.bridge.shape.Shape;
import ru.vasire.bridge.shape.Square;

public class BridgeToTest {
    public static void main(String[] args) {
        Shape square = new Square(4, new BlackColor());
        System.out.println("square perimeter = " + square.getPerimeter() + ", Area = " + square.getArea() + " " + square.color.getColor());

        Shape circle = new Circle(4, new RedColor());
        System.out.println("circle perimeter = " + circle.getPerimeter() + ", Area = " + circle.getArea() + " " + circle.color.getColor());

    }
}
