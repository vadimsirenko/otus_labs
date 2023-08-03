package ru.vasire.machine.service;

import org.springframework.stereotype.Service;
import ru.vasire.machine.exception.InvalidAccountException;
import ru.vasire.machine.model.Account;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService{

    private final Map<Account, BigDecimal> accountStore;

    public AccountServiceImpl() {
        this.accountStore = new HashMap<>();
        this.accountStore.put(new Account("12345", "1234"), BigDecimal.valueOf( 12345.12));
        this.accountStore.put(new Account("23456", "2345"), BigDecimal.valueOf( 23456.23));
        this.accountStore.put(new Account("34567", "3456"), BigDecimal.valueOf( 34567.34));
    }

    @Override
    public Account getAccount(String cardNumber, String pinCode) {
        Account account = accountStore.keySet().stream().filter(acc -> acc.equals(new Account(cardNumber, pinCode))).findFirst().orElse(null);
        if(account==null){
            throw new InvalidAccountException("Invalid card number or PIN code");
        }
        return account;
    }

    @Override
    public BigDecimal getBalance(Account account) {
        BigDecimal balance = accountStore.get(account);
        if(balance==null){
            throw new InvalidAccountException("Invalid account");
        }
        return balance;
    }

    @Override
    public BigDecimal changeBalance(Account account, BigDecimal delta) {
        BigDecimal balance = accountStore.get(account);
        if(balance==null){
            throw new InvalidAccountException("Invalid account");
        }
        accountStore.put(account, balance.add(delta));
        return accountStore.get(account);
    }
}
