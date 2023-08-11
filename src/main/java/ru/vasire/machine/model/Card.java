package ru.vasire.machine.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class Card {
    private String number;
    private String pinCode;
    private Account account;

    public Card(@NonNull String cardNumber, @NonNull String pinCode, @NonNull Account account) {
        this.number = cardNumber;
        this.pinCode = pinCode;
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Card) {
            if (((Card) o).number.equals(this.number)) {
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
