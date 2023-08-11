package ru.vasire.machine.service;

import org.springframework.stereotype.Service;
import ru.vasire.machine.exception.InvalidAccountException;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.Card;
import ru.vasire.machine.model.dto.CardDetailsDto;
import ru.vasire.machine.repository.AccountCardRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountCardRepository accountCardRepository;

    public AccountServiceImpl(AccountCardRepository accountCardRepository) {
        this.accountCardRepository = accountCardRepository;

        Account account = this.accountCardRepository.addAccount("123-456", BigDecimal.valueOf(12345.12));
        account.addCard("12345", "1234");

        account = this.accountCardRepository.addAccount("234-567", BigDecimal.valueOf(23456.23));
        account.addCard("23456", "2345");

        account = this.accountCardRepository.addAccount("345-678", BigDecimal.valueOf(34567.34));
        account.addCard("34567", "3456");
    }

    @Override
    public Account getAccountByCardNumber(String cardNumber) {
        return accountCardRepository.getAccountByCardNumber(cardNumber);
    }

    @Override
    public BigDecimal getBalanceByCardNumber(String cardNumber) {
        Account account = accountCardRepository.getAccountByCardNumber(cardNumber);
        if (account == null) {
            throw new InvalidAccountException("Invalid account");
        }
        return account.getBalance();
    }

    @Override
    public void changeBalance(String cardNumber, BigDecimal delta) {
        Account account = accountCardRepository.getAccountByCardNumber(cardNumber);
        if (account == null) {
            throw new InvalidAccountException("Invalid account");
        }
        account.setBalance(account.getBalance().add(delta));
    }

    @Override
    public List<CardDetailsDto> getCardDetails() {
        List<CardDetailsDto> cardDetailsDataList = new ArrayList<>();
        for (Account account : accountCardRepository.getAllAccounts()) {
            for (Card card : account.getCards()) {
                cardDetailsDataList.add(new CardDetailsDto(
                        account.getNumber(), card.getNumber(), card.getPinCode(), account.getBalance()));
            }
        }
        return cardDetailsDataList;
    }
}
