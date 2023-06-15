package ru.vasire.machine;

/**
 * Cash dispensing error
 */
public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
