package ru.vasire.machine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.vasire.machine.model.dto.CardDto;
import ru.vasire.machine.service.CardService;

@Component
public class CardValidator extends CardBaseValidator<CardDto> {

    public CardValidator(CardService cardService) {
        super(cardService);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CardDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        super.validate(target, errors);
    }
}
