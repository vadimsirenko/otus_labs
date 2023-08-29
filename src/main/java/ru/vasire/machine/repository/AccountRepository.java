package ru.vasire.machine.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.dto.CardDetailsDto;

import java.util.List;

public interface AccountRepository extends ListCrudRepository<Account, String> {
    @Query("select a.* from account a JOIN card c ON a.id = c.account_id WHERE c.card_number = :cardNumber")
    List<Account> findByCardNumber(@Param("cardNumber") String cardNumber);

    @Query(value = """
            select a.id           as accuntNumber,
                   a.balance        as balance,
                   c.card_number           as cardNumber,
                   c.pin_code         as pinCode
            from account a
                     left outer join card c
                                     on a.id = c.account_id
                                                          """,
            resultSetExtractorClass = CardDetailsDtoResultSetExtractorClass.class)
    List<CardDetailsDto> findAllAccountCards();
}
