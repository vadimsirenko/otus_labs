package ru.vasire.machine.service;

import ru.vasire.machine.model.BanknoteBundle;
import ru.vasire.machine.model.dto.AtmBanknoteDto;
import ru.vasire.machine.model.dto.AtmMoneyDto;
import ru.vasire.machine.model.dto.CardDto;

import java.math.BigDecimal;
import java.util.List;

public interface AtmService {

    public BigDecimal getBalance(CardDto cardDto);

    List<BanknoteBundle> getMoney(AtmMoneyDto atmMoneyData);

    public BigDecimal putMoney(AtmBanknoteDto atmBanknoteData);
}
