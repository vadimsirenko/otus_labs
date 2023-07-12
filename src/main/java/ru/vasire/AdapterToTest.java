package ru.vasire;

import ru.vasire.adapter.*;

public class AdapterToTest {
    public static void main(String[] args) {

        PaypalPayment paypalPayment = new PaypalPayment();
        Payment payment = new PaypalPaymentAdapter(paypalPayment);
        payment.connectToPaymentSystem();
        payment.sentMoney();
        payment.disconnectToPaymentSystem();

        System.out.println("*****************************");
        CreditCardPayment creditCardPayment = new CreditCardPayment();

        payment = new CreditCardPaymentAdapter(creditCardPayment);
        payment.connectToPaymentSystem();
        payment.sentMoney();
        payment.disconnectToPaymentSystem();


    }
}
