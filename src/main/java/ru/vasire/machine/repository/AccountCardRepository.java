package ru.vasire.machine.repository;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.Card;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class AccountCardRepository {
    private final Set<Account> accountStore;

    public Account addAccount(@NonNull String accountNumber, @NonNull BigDecimal balance) {
        Account account = new Account(accountNumber, balance);
        accountStore.add(account);
        return account;
    }

    public Account getAccountByCardNumber(String cardNumber) {
        return accountStore.stream()
                .filter(acc -> acc.getCards().stream().anyMatch(card -> card.getNumber().equals(cardNumber)))
                .findFirst()
                .orElse(null);
    }

    public List<Account> getAllAccounts() {
        return accountStore.stream().toList();
    }

    public Card findCardByCardNumberAndPinCode(String cardNumber) {
        for (Account account : accountStore) {
            for (Card card : account.getCards()) {
                if (card.getNumber().equals(cardNumber)) {
                    return card;
                }
            }
        }
        return null;
    }
}
