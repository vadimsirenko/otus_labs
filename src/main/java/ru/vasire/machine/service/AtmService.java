package ru.vasire.machine.service;

import ru.vasire.machine.model.BanknoteBundle;
import ru.vasire.machine.model.dto.AccountData;
import ru.vasire.machine.model.dto.AtmBanknoteData;
import ru.vasire.machine.model.dto.AtmMoneyData;

import java.math.BigDecimal;
import java.util.List;

public interface AtmService {

    public BigDecimal getBalance(AccountData accountData);

    List<BanknoteBundle> getMoney(AtmMoneyData atmMoneyData);

    public BigDecimal putMoney(AtmBanknoteData atmBanknoteData);
}
