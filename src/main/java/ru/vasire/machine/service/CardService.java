package ru.vasire.machine.service;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public interface CardService {
    public boolean verifyPinCode(String cardNumber, String pinCode);

    public BigDecimal getBalance(@NotNull String cardNumber, @NotNull String pinCode);

    boolean changePinCode(String cardNumber, String pinCode, String newPinCode);

    void changeBalance(String cardNumber, String pinCode, BigDecimal delta);
}