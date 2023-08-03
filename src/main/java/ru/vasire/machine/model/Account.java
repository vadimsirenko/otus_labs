package ru.vasire.machine.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


public class Account {
    private String cardNumber;
    private String pinCode;

    public Account(@NonNull String cardNumber, @NonNull String pinCode) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Account){
            if(((Account) o).cardNumber.equals(this.cardNumber) &&
                    ((Account) o).pinCode.equals(this.pinCode)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = cardNumber != null ? cardNumber.hashCode() : 0;
        result = 31 * result + (pinCode != null ? pinCode.hashCode() : 0);
        return result;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
