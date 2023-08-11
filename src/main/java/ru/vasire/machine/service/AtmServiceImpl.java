package ru.vasire.machine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vasire.machine.model.Banknote;
import ru.vasire.machine.model.BanknoteBundle;
import ru.vasire.machine.model.BanknoteBundleUtils;
import ru.vasire.machine.model.BanknoteCell;
import ru.vasire.machine.model.dto.AtmBanknoteDto;
import ru.vasire.machine.model.dto.AtmMoneyDto;
import ru.vasire.machine.model.dto.CardDto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtmServiceImpl implements AtmService {

    private final CashMachine cashMachine = new CashMachineSimple(Arrays.asList(
            new BanknoteCell(Banknote.N100, 5),
            new BanknoteCell(Banknote.N500, 5),
            new BanknoteCell(Banknote.N1000, 5),
            new BanknoteCell(Banknote.N5000, 5)));

    private final CardService cardService;

    @Override
    public BigDecimal getBalance(CardDto cardDto) {
        return cardService.getBalance(cardDto.getCardNumber(), cardDto.getPinCode());
    }

    @Override
    public List<BanknoteBundle> getMoney(AtmMoneyDto atmMoneyData) {
        List<Banknote> banknotes = cashMachine.getMoney(atmMoneyData.getSum());
        cardService.changeBalance(atmMoneyData.getCardNumber(), atmMoneyData.getPinCode(), BigDecimal.valueOf(-atmMoneyData.getSum()));
        return BanknoteBundleUtils.banknoteListToBanknoteBundleList(banknotes);
    }

    @Override
    public BigDecimal putMoney(AtmBanknoteDto atmBanknoteData) {
        BanknoteBundle banknoteBundle = new BanknoteBundle(atmBanknoteData.getBanknote(), atmBanknoteData.getBanknoteCount());
        cashMachine.putMoney(Collections.nCopies(atmBanknoteData.getBanknoteCount(), atmBanknoteData.getBanknote()));
        cardService.changeBalance(atmBanknoteData.getCardNumber(), atmBanknoteData.getPinCode(), BigDecimal.valueOf(banknoteBundle.getBalance()));
        return getBalance(atmBanknoteData);
    }
}
