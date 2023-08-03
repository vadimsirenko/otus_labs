package ru.vasire.machine.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
public class AtmData {
    @NotEmpty(message = "The card number field cannot be empty")
    private String cardNumber;
    @NotEmpty(message = "Поле пин-код не может быть пустой")
    private String pinCode;
    @NotEmpty(message = "Requested amount cannot be empty")
    @Positive(message = "The sum must be positive")
    private int sum;

    private BigDecimal balance;

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

    public int getSum() {
        return sum;
    }
    public void setSum(int sum) {
        this.sum = sum;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
