package ru.vasire.machine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.vasire.machine.model.dto.AccountChangePinCodeData;
import ru.vasire.machine.model.dto.AtmMoneyData;
import ru.vasire.machine.service.AccountService;

@Component
public class AccountChangePinCodeValidator  extends AccountDataBaseValidator<AtmMoneyData> {

    public AccountChangePinCodeValidator(AccountService accountService) {
        super(accountService);
    }

    @Override
    public boolean supports(Class<?> clazz) { return AccountChangePinCodeData.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        AccountChangePinCodeData account = (AccountChangePinCodeData) target;
        super.validate(target, errors);
        if(account.getNewPinCode()==null || !account.getNewPinCode().equals(account.getNewPinCode2())){
            errors.rejectValue("newPinCode2", null, "The new PIN code and new PIN code 2 do not match.");
        }
    }
}
