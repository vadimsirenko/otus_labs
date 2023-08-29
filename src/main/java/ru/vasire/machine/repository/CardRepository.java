package ru.vasire.machine.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.vasire.machine.model.Card;

import java.math.BigInteger;
import java.util.Optional;

public interface CardRepository extends ListCrudRepository<Card, BigInteger> {
    @Modifying
    @Query("update card set pin_code = :newPinCode where id = :id")
    void updatePinCode(@Param("id") long id, @Param("newPinCode") String newName);

    Optional<Card> findByCardNumber(String cardNumber);
}
