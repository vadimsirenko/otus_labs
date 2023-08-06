package ru.vasire.machine.service;

import org.springframework.stereotype.Service;
import ru.vasire.machine.exception.InvalidAccountException;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.dto.AccountDetailsData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    public boolean validatePinCode(Account account, String pinCode) {
        if(!account.getPinCode().equals(pinCode))
            return false;
        return true;
    }
    @Override
    public Account getAccountByCardNumber(String cardNumber) {
        Account account = accountStore.keySet().stream().filter(acc -> acc.equals(new Account(cardNumber,""))).findFirst().orElse(null);
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
    public void changeBalance(Account account, BigDecimal delta) {
        BigDecimal balance = accountStore.get(account);
        if(balance==null){
            throw new InvalidAccountException("Invalid account");
        }
        accountStore.put(account, balance.add(delta));
    }

    @Override
    public List<AccountDetailsData> getAccounts() {
        List<AccountDetailsData> accountDetailsDataList = new ArrayList<>(accountStore.size());
        for(Map.Entry<Account, BigDecimal> accountSet: accountStore.entrySet()){
            accountDetailsDataList.add(new AccountDetailsData(
                    accountSet.getKey().getCardNumber(), accountSet.getKey().getPinCode(), accountSet.getValue()));
        }
        return accountDetailsDataList;
    }

    @Override
    public boolean changePinCode(Account account, String newPinCode) {
        BigDecimal balance = accountStore.get(account);
        if(balance==null){
            throw new InvalidAccountException("Invalid account");
        }
        account.setPinCode(newPinCode);
        accountStore.remove(account);
        accountStore.put(account, balance);
        return true;
    }
}
