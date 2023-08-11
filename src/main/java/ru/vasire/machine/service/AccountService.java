package ru.vasire.machine.service;

import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.dto.CardDetailsDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account getAccountByCardNumber(String cardNumber);

    BigDecimal getBalanceByCardNumber(String cardNumber);

    void changeBalance(String cardNumber, BigDecimal delta);

    List<CardDetailsDto> getCardDetails();
}
