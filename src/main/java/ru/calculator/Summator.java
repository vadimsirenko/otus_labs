package ru.calculator;

import java.util.ArrayList;
import java.util.List;

public class Summator {
    private int sum = 0;
    private int prevValue = 0;
    private int prevPrevValue = 0;
    private int sumLastThreeValues = 0;
    private int someValue = 0;
    //private final List<Data> listValues = new ArrayList<>();
    private int listValues_size = 0;

    //!!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        //listValues.add(data);
        listValues_size++;
        //if (listValues.size() % 6_600_000 == 0) {
          //  listValues.clear();
        //}
        if (listValues_size % 6_600_000 == 0) {
            listValues_size = 0;
        }
        sum += data.getValue();

        sumLastThreeValues = data.getValue() + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = data.getValue();

        for (var idx = 0; idx < 3; idx++) {
            someValue += (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum);
            //someValue = Math.abs(someValue) + listValues.size();
            someValue = Math.abs(someValue) + listValues_size;
        }
    }

    public int getSum() {
        return sum;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public int getPrevPrevValue() {
        return prevPrevValue;
    }

    public int getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public int getSomeValue() {
        return someValue;
    }
}
