package ru.vasire.machine.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.vasire.machine.model.dto.CardDto;
import ru.vasire.machine.service.CardService;

@Component
@RequiredArgsConstructor
public class CardBaseValidator<T extends CardDto> implements Validator {

    protected final CardService cardService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CardDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        T accountData = (T) target;
        try {
            if (!cardService.verifyPinCode(accountData.getCardNumber(), accountData.getPinCode())) {
                errors.rejectValue("cardNumber", null, "Invalid card number or pin code");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.rejectValue("cardNumber", null, "Credit card verification error");
        }
    }
}
