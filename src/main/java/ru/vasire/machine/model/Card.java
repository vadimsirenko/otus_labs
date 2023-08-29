package ru.vasire.machine.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table("card")
public class Card {
    @Id
    private final Long id;
    @NonNull
    private final String cardNumber;
    @Nonnull
    private final String accountId;
    @Transient
    private final boolean isNew;
    @NonNull
    private String pinCode;

    public Card(Long id, @NonNull String cardNumber, @NonNull String pinCode, @NonNull String accountId, boolean isNew) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.accountId = accountId;
        this.isNew = isNew;
    }

    @PersistenceCreator
    public Card(Long id, @NonNull String cardNumber, @NonNull String pinCode, @NonNull String accountId) {
        this(id, cardNumber, pinCode, accountId, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Card) {
            if (((Card) o).cardNumber.equals(this.cardNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return cardNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
