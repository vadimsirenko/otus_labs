package ru.vasire.machine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vasire.machine.model.*;
import ru.vasire.machine.model.dto.AtmData;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtmCashMachineServiceImpl implements AtmCashMachineService {

    private final CashMachine cashMachine = new CashMachineSimple( List.of(
            new BanknoteCell(Banknote.N100, 5),
            new BanknoteCell(Banknote.N500, 5),
            new BanknoteCell(Banknote.N1000, 5),
            new BanknoteCell(Banknote.N5000, 5)));

    private final AccountService accountService;


    public BigDecimal getBalance(AtmData atmRequest) {
        return accountService.getBalance(new Account(atmRequest.getCardNumber(), atmRequest.getPinCode()));
    }

    @Override
    public List<BanknoteBundle> getMoney(AtmData atmRequest) {
        List<Banknote> banknotes = cashMachine.getMoney(atmRequest.getSum());
        accountService.changeBalance(new Account(atmRequest.getCardNumber(), atmRequest.getPinCode()), BigDecimal.valueOf(-atmRequest.getSum()));
        return BanknoteBundleUtils.banknoteListToBanknoteBundleList(banknotes);
    }
}
