package ru.vasire.machine.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class Account {
    private String number;
    private BigDecimal balance;
    private Set<Card> cards;

    public Account(@NonNull String number, @NonNull BigDecimal balance) {
        this.number = number;
        this.balance = balance;
        this.cards = new HashSet<>();
    }

    public void addCard(String cardNumber, String pinCode) {
        Card card = new Card(cardNumber, pinCode, this);
        cards.add(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Account) {
            if (((Account) o).number.equals(this.number)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }
}
