package ru.vasire.machine.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vasire.machine.exception.InvalidAccountException;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.dto.CardDetailsDto;
import ru.vasire.machine.repository.AccountRepository;
import ru.vasire.machine.sessionmanager.TransactionManager;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final TransactionManager transactionManager;
    private final AccountRepository accountRepository;

    @Override
    public Account getAccountByCardNumber(String cardNumber) {
        var accountList = accountRepository.findByCardNumber(cardNumber);
        if (accountList == null || accountList.size() > 1) {
            log.error(String.format("More than one account was found for the card %s", cardNumber));
            throw new InvalidAccountException("Invalid cardNumber");
        }
        return accountList.get(0);
    }

    @Override
    public BigDecimal getBalanceByCardNumber(String cardNumber) {
        Account account = getAccountByCardNumber(cardNumber);
        return account.getBalance();
    }

    @Override
    public void changeBalance(String cardNumber, BigDecimal delta) {
        Account account = getAccountByCardNumber(cardNumber);
        account.setBalance(account.getBalance().add(delta));
        transactionManager.doCommandInTransaction(() -> {
            var savedAccount = accountRepository.save(account);
            log.info("saved account: {}", savedAccount);
            return null;
        });
    }

    @Override
    public List<CardDetailsDto> getCardDetails() {
        return accountRepository.findAllAccountCards();
    }
}
