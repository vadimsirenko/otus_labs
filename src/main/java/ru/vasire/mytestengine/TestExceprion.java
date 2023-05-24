package ru.vasire.mytestengine;

public class TestExceprion extends RuntimeException{
    public TestExceprion(String errorMessage){
        super(errorMessage);
    }
}
