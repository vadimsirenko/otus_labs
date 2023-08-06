package ru.vasire.machine.service;

import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.dto.AccountDetailsData;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account getAccountByCardNumber(String cardNumber);
    boolean validatePinCode(Account account, String pinCode);
    boolean changePinCode(Account account, String newPinCode);
    BigDecimal getBalance(Account account);
    void changeBalance(Account account, BigDecimal delta);
    List<AccountDetailsData> getAccounts();
}
