package ru.vasire.machine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.vasire.machine.model.dto.AtmBanknoteDto;
import ru.vasire.machine.model.dto.AtmMoneyDto;
import ru.vasire.machine.service.CardService;

@Component
public class AtmBanknoteValidator extends CardBaseValidator<AtmMoneyDto> {

    public AtmBanknoteValidator(CardService cardService) {
        super(cardService);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AtmBanknoteDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AtmBanknoteDto atmGetMoneyRequest = (AtmBanknoteDto) target;
        super.validate(target, errors);

        if (atmGetMoneyRequest.getBanknoteCount() <= 0) {
            errors.rejectValue("banknoteCount", null, "The number of banknotes must be greater than zero");
            return;
        }
    }
}
