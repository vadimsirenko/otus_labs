package ru.vasire.machine.model;

import jakarta.annotation.Nonnull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Table("account")
public class Account {
    @Transient
    private final boolean isNew;
    @Id
    @Nonnull
    @Column("id")
    private String number;
    @Nonnull
    private BigDecimal balance;
    @MappedCollection(idColumn = "account_id")
    private Set<Card> cards;

    public Account(@NonNull String number, @NonNull BigDecimal balance) {
        this(number, balance, new HashSet<>(), false);
    }

    public Account(@NonNull String number, @NonNull BigDecimal balance, Set<Card> cards, boolean isNew) {
        this.number = number;
        this.balance = balance;
        this.cards = cards;
        this.isNew = isNew;
    }

    @PersistenceCreator
    public Account(@NonNull String number, @NonNull BigDecimal balance, Set<Card> cards) {
        this(number, balance, cards, false);
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

    @Override
    public String toString() {
        return "Account{" +
                "number='" + number + '\'' +
                ", balance=" + balance +
                ", cards=" + cards +
                ", isNew=" + isNew +
                '}';
    }
}
