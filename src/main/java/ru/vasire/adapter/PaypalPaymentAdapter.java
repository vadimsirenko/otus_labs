package ru.vasire.adapter;

public class PaypalPaymentAdapter implements Payment {

    private final PaypalPayment paypalPayment;

    public PaypalPaymentAdapter(PaypalPayment paypalPayment) {
        this.paypalPayment = paypalPayment;
    }

    @Override
    public void connectToPaymentSystem() {
        paypalPayment.loginToPaypalPayment();
    }

    @Override
    public void disconnectToPaymentSystem() {
        paypalPayment.logoutToPaypalPayment();
    }

    @Override
    public void sentMoney() {
        paypalPayment.executePaymentToPaypalPayment();
    }
}
