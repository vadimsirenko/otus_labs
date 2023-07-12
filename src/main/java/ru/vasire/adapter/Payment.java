package ru.vasire.adapter;

public interface Payment {
    void connectToPaymentSystem();

    void disconnectToPaymentSystem();

    void sentMoney();
}
