package ru.vasire.machine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.vasire.machine.model.dto.AtmMoneyDto;
import ru.vasire.machine.model.dto.CardChangePinCodeDto;
import ru.vasire.machine.service.CardService;

@Component
public class CardChangePinCodeValidator extends CardBaseValidator<AtmMoneyDto> {

    public CardChangePinCodeValidator(CardService cardService) {
        super(cardService);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CardChangePinCodeDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CardChangePinCodeDto account = (CardChangePinCodeDto) target;
        super.validate(target, errors);
        if (account.getNewPinCode() == null || !account.getNewPinCode().equals(account.getNewPinCode2())) {
            errors.rejectValue("newPinCode2", null, "The new PIN code and new PIN code 2 do not match.");
        }
    }
}
