package ru.vasire.machine.service;

import org.springframework.stereotype.Service;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.Banknote;
import ru.vasire.machine.model.BanknoteBundle;
import ru.vasire.machine.model.BanknoteBundleUtils;
import ru.vasire.machine.model.BanknoteCell;
import ru.vasire.machine.model.dto.AccountData;
import ru.vasire.machine.model.dto.AtmBanknoteData;
import ru.vasire.machine.model.dto.AtmMoneyData;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AtmServiceImpl implements AtmService {

    private final CashMachine cashMachine = new CashMachineSimple( Arrays.asList(
            new BanknoteCell(Banknote.N100, 5),
            new BanknoteCell(Banknote.N500, 5),
            new BanknoteCell(Banknote.N1000, 5),
            new BanknoteCell(Banknote.N5000, 5)));

    private final AccountService accountService;

    public AtmServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    public BigDecimal getBalance(AccountData accountData) {
        return accountService.getBalance(new Account(accountData.getCardNumber(), accountData.getPinCode()));
    }

    @Override
    public List<BanknoteBundle> getMoney(AtmMoneyData atmMoneyData) {
        List<Banknote> banknotes = cashMachine.getMoney(atmMoneyData.getSum());
        accountService.changeBalance(new Account(atmMoneyData.getCardNumber(), atmMoneyData.getPinCode()), BigDecimal.valueOf(-atmMoneyData.getSum()));
        return BanknoteBundleUtils.banknoteListToBanknoteBundleList(banknotes);
    }

    @Override
    public BigDecimal putMoney(AtmBanknoteData atmBanknoteData) {
        BanknoteBundle banknoteBundle = new BanknoteBundle(atmBanknoteData.getBanknote(), atmBanknoteData.getBanknoteCount());
        cashMachine.putMoney(Collections.nCopies(atmBanknoteData.getBanknoteCount(), atmBanknoteData.getBanknote()));
        accountService.changeBalance(new Account(atmBanknoteData.getCardNumber(), atmBanknoteData.getPinCode()),
                BigDecimal.valueOf(banknoteBundle.getBalance()));
        return getBalance(atmBanknoteData);
    }
}
