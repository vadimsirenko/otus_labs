package ru.vasire.machine.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vasire.machine.exception.InvalidAccountException;
import ru.vasire.machine.model.Card;
import ru.vasire.machine.repository.CardRepository;
import ru.vasire.machine.sessionmanager.TransactionManager;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountService accountService;
    private final CardRepository cardRepository;
    private final TransactionManager transactionManager;

    private static boolean verifyCard(Card card, @NotNull String pinCode) {
        return card != null && card.getPinCode().equals(pinCode);
    }

    private Card getCardByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber).orElseThrow(() -> new InvalidAccountException("Invalid card number"));
    }

    @Override
    public boolean verifyPinCode(@NotNull String cardNumber, @NotNull String pinCode) {
        Card card = getCardByCardNumber(cardNumber);
        return verifyCard(card, pinCode);
    }

    @Override
    public BigDecimal getBalance(@NotNull String cardNumber, @NotNull String pinCode) {
        if (!verifyPinCode(cardNumber, pinCode)) {
            throw new InvalidAccountException("Invalid card number or pin code");
        }
        return accountService.getBalanceByCardNumber(cardNumber);
    }

    @Override
    public boolean changePinCode(@NotNull String cardNumber, @NotNull String pinCode, @NotNull String newPinCode) {
        Card card = getCardByCardNumber(cardNumber);
        if (!verifyCard(card, pinCode)) {
            throw new InvalidAccountException("Invalid card number or pin code");
        }
        card.setPinCode(newPinCode);
        transactionManager.doCommandInTransaction(() -> {
            cardRepository.updatePinCode(card.getId(), newPinCode);
            log.info("saved card: {}", card);
            return null;
        });
        return true;
    }

    @Override
    public void changeBalance(String cardNumber, String pinCode, BigDecimal delta) {
        Card card = getCardByCardNumber(cardNumber);
        if (!verifyCard(card, pinCode)) {
            throw new InvalidAccountException("Invalid card number or pin code");
        }
        accountService.changeBalance(cardNumber, delta);
    }
}
