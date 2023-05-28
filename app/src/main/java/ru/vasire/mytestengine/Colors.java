package ru.vasire.mytestengine;

public class Colors {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void printRed(String message){
        System.out.println(ANSI_RED + message + ": FAILED!" +  ANSI_RESET);
    }

    public static void printGreen(String message){
        System.out.println(ANSI_GREEN + message + ": PASSED!" +  ANSI_RESET);
    }
}
