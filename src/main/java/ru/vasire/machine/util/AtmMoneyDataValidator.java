package ru.vasire.machine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.vasire.machine.model.Account;
import ru.vasire.machine.model.dto.AtmMoneyData;
import ru.vasire.machine.service.AccountService;

@Component
public class AtmMoneyDataValidator extends AccountDataBaseValidator<AtmMoneyData> {

    public AtmMoneyDataValidator(AccountService accountService) {
        super(accountService);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return AtmMoneyData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AtmMoneyData atmGetMoneyRequest = (AtmMoneyData) target;
        super.validate(target, errors);

        if (errors.getErrorCount() > 0)
            return;
        try {
            Integer balance = accountService.getBalance(new Account(atmGetMoneyRequest.getCardNumber(), atmGetMoneyRequest.getPinCode())).intValue();
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
