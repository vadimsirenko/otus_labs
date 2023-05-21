package ru.vasire;

import ru.vasire.model.Box;
import ru.vasire.model.fruit.Apple;
import ru.vasire.model.fruit.Fruit;
import ru.vasire.model.fruit.Orange;

public class testApp{
    public static void main(String[] args) {
        Box<Apple> boxApple1 = new Box<>();
        boxApple1.addFruit(new Apple());
        boxApple1.addFruit(new Apple());
        boxApple1.addFruit(new Apple());

        Box<Apple> boxApple2 = new Box<>();
        boxApple2.addFruit(new Apple());
        boxApple2.addFruit(new Apple());
        boxApple2.addFruit(new Apple());
        boxApple2.addFruit(new Apple());

        Box<Orange> boxOrange = new Box<>();
        boxOrange.addFruit(new Orange());
        boxOrange.addFruit(new Orange());
        boxOrange.addFruit(new Orange());

        System.out.println("Вес 1-го ящика с яблоками " + boxApple1.weight());
        System.out.println("Вес 2-го ящика с яблоками " + boxApple2.weight());
        System.out.println("Вес ящика с апельсинами " + boxOrange.weight());

        System.out.println("Вес ящика с апельсинами и 1-го с яблоками равны? " + boxOrange.compare(boxApple1));
        System.out.println("Вес ящика с апельсинами и 2-го с яблоками равны? " + boxOrange.compare(boxApple2));

        boxApple1.pourIntoOtherBasket(boxApple2);
        System.out.println("Пересыпали яблоки из первого ящика во второй");
        System.out.println("Вес 1-го ящика с яблоками " + boxApple1.weight());
        System.out.println("Вес 2-го ящика с яблоками " + boxApple2.weight());

    }
}
