package ru.vasire.mytestengine;

import static ru.vasire.mytestengine.Colors.*;

public final class Assertions {

    public static void isTrue(boolean ruleResult, String message){
        if(ruleResult){
            printGreen(message);
        }else{
            throw new TestExceprion(message+ ": FAILED");
        }
    }
}
