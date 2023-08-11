package ru.vasire.machine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.vasire.machine.model.dto.AtmMoneyDto;
import ru.vasire.machine.service.CardService;

@Component
public class AtmMoneyValidator extends CardBaseValidator<AtmMoneyDto> {

    public AtmMoneyValidator(CardService cardService) {
        super(cardService);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AtmMoneyDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AtmMoneyDto atmGetMoneyRequest = (AtmMoneyDto) target;
        super.validate(target, errors);

        if (errors.getErrorCount() > 0)
            return;
        try {
            Integer balance = cardService.getBalance(atmGetMoneyRequest.getCardNumber(), atmGetMoneyRequest.getPinCode()).intValue();
            if (atmGetMoneyRequest.getSum() > balance) {
                errors.rejectValue("sum", null, "Requested amount exceeds balance");
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            errors.rejectValue("sum", null, "Error getting balance");
        }
    }
}
