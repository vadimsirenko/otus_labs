package ru.vasire.machine.service;

import ru.vasire.machine.model.Banknote;
import ru.vasire.machine.model.BanknoteBundle;
import ru.vasire.machine.model.dto.AtmData;

import java.math.BigDecimal;
import java.util.List;

public interface AtmCashMachineService {

    public BigDecimal getBalance(AtmData atmRequest);

    List<BanknoteBundle> getMoney(AtmData atmRequest);

}
