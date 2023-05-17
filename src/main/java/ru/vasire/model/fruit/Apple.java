package ru.vasire.model.fruit;

public class Apple extends Fruit  implements BoxedFruit{
    @Override
    public int getWeight() {
        return 150;
    }
}
