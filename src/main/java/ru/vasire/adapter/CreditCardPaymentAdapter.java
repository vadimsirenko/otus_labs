package ru.vasire.adapter;

public class CreditCardPaymentAdapter implements Payment {

    private final CreditCardPayment creditCardPayment;

    public CreditCardPaymentAdapter(CreditCardPayment creditCardPayment) {
        this.creditCardPayment = creditCardPayment;
    }

    @Override
    public void connectToPaymentSystem() {
        creditCardPayment.loginToCreditCardPayment();
    }

    @Override
    public void disconnectToPaymentSystem() {
        creditCardPayment.logoutToCreditCardPayment();
    }

    @Override
    public void sentMoney() {
        creditCardPayment.executePayment();
    }
}
