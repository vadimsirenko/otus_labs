package ru.vasire.machine.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vasire.machine.exception.InvalidAccountException;
import ru.vasire.machine.model.Card;
import ru.vasire.machine.repository.AccountCardRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final AccountService accountService;
    private final AccountCardRepository accountCardRepository;

    private static boolean verifyCard(Card card, @NotNull String pinCode) {
        return card != null && card.getPinCode().equals(pinCode);
    }

    @Override
    public boolean verifyPinCode(@NotNull String cardNumber, @NotNull String pinCode) {
        Card card = accountCardRepository.findCardByCardNumberAndPinCode(cardNumber);
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
        Card card = accountCardRepository.findCardByCardNumberAndPinCode(cardNumber);
        if (!verifyCard(card, pinCode)) {
            throw new InvalidAccountException("Invalid card number or pin code");
        }
        card.setPinCode(newPinCode);
        return true;
    }

    @Override
    public void changeBalance(String cardNumber, String pinCode, BigDecimal delta) {
        Card card = accountCardRepository.findCardByCardNumberAndPinCode(cardNumber);
        if (!verifyCard(card, pinCode)) {
            throw new InvalidAccountException("Invalid card number or pin code");
        }
        accountService.changeBalance(cardNumber, delta);
    }
}
