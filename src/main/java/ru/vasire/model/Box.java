package ru.vasire.model;

import ru.vasire.model.fruit.BoxedFruit;
import ru.vasire.model.fruit.Fruit;

import java.util.ArrayList;

public class Box<T extends Fruit & BoxedFruit> {
    ArrayList<T> items = new ArrayList<>();

    public void addFruit(T item){
        items.add(item);
    }

    // вес коробки из веса одного фрукта и их количества
    public int weight(){
        if (items.isEmpty()){
            return 0;
        }
        return items.size() * items.get(0).getWeight();
    }

    // сравнить текущую коробку с той, которую подадут
    public boolean compare(Box<?> otherBox){
        if(otherBox==null)
            return false;
        return otherBox.weight() == this.weight();
    }

    // пересыпать фрукты из текущей коробки в другую (с такими же фруктами)
    public void pourIntoOtherBasket(Box<T> otherBox){
        otherBox.items.addAll(this.items);
        this.items.clear();
    }

}
