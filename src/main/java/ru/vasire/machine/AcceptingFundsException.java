package ru.vasire.machine;

/**
 * Error in accepting funds
 */
public class AcceptingFundsException extends Exception {
    public AcceptingFundsException(String message) {
        super(message);
    }
}
