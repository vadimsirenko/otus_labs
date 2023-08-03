package ru.vasire.machine.service;

import ru.vasire.machine.model.Account;

import java.math.BigDecimal;
import java.time.format.DecimalStyle;

public interface AccountService {
    Account getAccount(String cardNumber, String pinCode);
    BigDecimal getBalance(Account account);
    BigDecimal changeBalance(Account account, BigDecimal delta);
}
