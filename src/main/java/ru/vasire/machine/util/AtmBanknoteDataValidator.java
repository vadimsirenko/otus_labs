package ru.vasire.machine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.vasire.machine.model.dto.AtmBanknoteData;
import ru.vasire.machine.model.dto.AtmMoneyData;
import ru.vasire.machine.service.AccountService;

@Component
public class AtmBanknoteDataValidator extends AccountDataBaseValidator<AtmMoneyData> {

    public AtmBanknoteDataValidator(AccountService accountService) {
        super(accountService);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AtmBanknoteData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AtmBanknoteData atmGetMoneyRequest = (AtmBanknoteData) target;
        super.validate(target, errors);

        if(atmGetMoneyRequest.getBanknoteCount()<=0){
            errors.rejectValue("banknoteCount", null, "The number of banknotes must be greater than zero");
            return;
        }
    }
}
